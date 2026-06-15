package com.example.alumniconfluxbackend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    private static final URI BREVO_API_URI = URI.create("https://api.brevo.com/v3/smtp/email");
    private final Map<String, OtpData> otpStorage = new ConcurrentHashMap<>();
    private static final int OTP_EXPIRY_MINUTES = 5;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @org.springframework.beans.factory.annotation.Value("${brevo.api.key}")
    private String brevoApiKey;

    @org.springframework.beans.factory.annotation.Value("${brevo.from.email}")
    private String fromEmail;

    @org.springframework.beans.factory.annotation.Value("${brevo.from.name:Alumni Conflux}")
    private String fromName;

    public OtpService() {
    }

    public String generateOtp(String email, String purpose) {
        String otp = String.format("%06d", new Random().nextInt(1000000));
        otpStorage.put(email, new OtpData(otp, LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES)));
        System.out.println("Generated OTP for " + email + " (" + purpose + "): " + otp); // For debugging purposes
        sendOtpEmail(email, otp, purpose);
        return otp;
    }

    public String generateOtp(String email) {
        return generateOtp(email, "Verification");
    }

    private void sendOtpEmail(String email, String otp, String purpose) {
        try {
            String subject = "Your Verification Code";
            String body = "Your verification code for Alumni Conflux is: " + otp + "\n\nThis code will expire in "
                    + OTP_EXPIRY_MINUTES + " minutes.";
            String html = "<p>Your verification code for Alumni Conflux is:</p>"
                    + "<h2 style=\"letter-spacing: 4px;\">" + otp + "</h2>"
                    + "<p>This code will expire in " + OTP_EXPIRY_MINUTES + " minutes.</p>";

            if ("SIGNUP".equalsIgnoreCase(purpose)) {
                subject = "Welcome to Alumni Conflux - Verify Your Email";
                body = "Welcome to Alumni Conflux! To complete your registration, please use the following verification code:\n\n"
                        + "Verification Code: " + otp + "\n\n"
                        + "This code will expire in " + OTP_EXPIRY_MINUTES
                        + " minutes. If you did not request this, please ignore this email.";
                html = "<p>Welcome to Alumni Conflux! To complete your registration, please use the following verification code:</p>"
                        + "<h2 style=\"letter-spacing: 4px;\">" + otp + "</h2>"
                        + "<p>This code will expire in " + OTP_EXPIRY_MINUTES
                        + " minutes. If you did not request this, please ignore this email.</p>";
            } else if ("FORGOT_PASSWORD".equalsIgnoreCase(purpose)) {
                subject = "Reset Your Password - Alumni Conflux";
                body = "We received a request to reset your password for your Alumni Conflux account. Please use the following code to proceed:\n\n"
                        + "Reset Code: " + otp + "\n\n"
                        + "This code will expire in " + OTP_EXPIRY_MINUTES + " minutes.";
                html = "<p>We received a request to reset your password for your Alumni Conflux account.</p>"
                        + "<p>Please use the following code to proceed:</p>"
                        + "<h2 style=\"letter-spacing: 4px;\">" + otp + "</h2>"
                        + "<p>This code will expire in " + OTP_EXPIRY_MINUTES + " minutes.</p>";
            }

            Map<String, Object> sender = new LinkedHashMap<>();
            sender.put("name", fromName);
            sender.put("email", fromEmail);

            Map<String, Object> recipient = new LinkedHashMap<>();
            recipient.put("email", email);

            Map<String, Object> requestBody = new LinkedHashMap<>();
            requestBody.put("sender", sender);
            requestBody.put("to", List.of(recipient));
            requestBody.put("subject", subject);
            requestBody.put("htmlContent", html);

            String payload = objectMapper.writeValueAsString(requestBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(BREVO_API_URI)
                    .header("api-key", brevoApiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 201) {
                throw new IllegalStateException(
                        "Brevo API returned status " + response.statusCode() + ": " + response.body());
            }

            System.out.println("OTP email sent successfully to " + email + " for " + purpose);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("CRITICAL ERROR: Failed to send OTP email to " + email + ". Error: " + e.getMessage());
            throw new IllegalStateException("Failed to send OTP email", e);
        } catch (IOException e) {
            System.err.println("CRITICAL ERROR: Failed to send OTP email to " + email + ". Error: " + e.getMessage());
            throw new IllegalStateException("Failed to send OTP email", e);
        }
    }

    public boolean validateOtp(String email, String otp, boolean removeAfterValidation) {
        OtpData data = otpStorage.get(email);
        if (data == null) {
            return false;
        }
        if (data.expiryTime.isBefore(LocalDateTime.now())) {
            otpStorage.remove(email);
            return false;
        }
        boolean isValid = data.otp.equals(otp);
        if (isValid && removeAfterValidation) {
            otpStorage.remove(email);
        }
        return isValid;
    }

    private static class OtpData {
        String otp;
        LocalDateTime expiryTime;

        OtpData(String otp, LocalDateTime expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }
    }
}
