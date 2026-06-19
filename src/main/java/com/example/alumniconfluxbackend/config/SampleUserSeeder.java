package com.example.alumniconfluxbackend.config;

import com.example.alumniconfluxbackend.model.Alumni;
import com.example.alumniconfluxbackend.model.Student;
import com.example.alumniconfluxbackend.model.User;
import com.example.alumniconfluxbackend.repository.UserRepository;
import com.example.alumniconfluxbackend.util.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SampleUserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SampleUserSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        createAdmin();
        createMentorshipReadyAlumni();
        createStudent();
    }

    private void createAdmin() {
        String username = "admin";
        String email = "admin@alumniconflux.com";

        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
            return;
        }

        User user = new User();
        user.setFullName("Site Administrator");
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRole(Role.ADMIN);

        userRepository.save(user);
    }

    private void createMentorshipReadyAlumni() {
        List<AlumniSeed> alumniSeeds = List.of(
                new AlumniSeed("alumni1", "alumni1@alumniconflux.com", "Ali Mazhar", "University of Education", (short) 2018, "Software Engineering", "TechNova", "Senior Software Engineer", "5 years", List.of("Java", "Spring Boot", "Mentoring"), List.of("Published open source library"), List.of("Software Development", "Leadership"), "Stay curious and keep building."),
                new AlumniSeed("alumni2", "alumni2@alumniconflux.com", "M Ali", "University of Education", (short) 2019, "Data Science", "DataBridge", "Data Scientist", "4 years", List.of("Python", "Machine Learning", "Data Visualization"), List.of("Built predictive dashboards"), List.of("Career transitions", "Data roles"), "Focus on real projects and practical skills."),
                new AlumniSeed("alumni3", "alumni3@alumniconflux.com", "Saqib", "University of Education", (short) 2017, "Product Management", "ProductFlow", "Product Manager", "6 years", List.of("Product Strategy", "UX", "Stakeholder Management"), List.of("Launched mobile products"), List.of("Product careers", "Interview prep"), "Listen to users first, then build."),
                new AlumniSeed("alumni4", "alumni4@alumniconflux.com", "Amna Tahir", "University of Education", (short) 2020, "Cybersecurity", "SecureNet", "Security Analyst", "3 years", List.of("Security", "Risk Assessment", "Cloud"), List.of("Improved company security posture"), List.of("Cybersecurity careers", "Certifications"), "Keep learning and practice with labs."),
                new AlumniSeed("alumni5", "alumni5@alumniconflux.com", "Shazaib", "University of Education", (short) 2016, "Business Analytics", "InsightWorks", "Analytics Lead", "7 years", List.of("Analytics", "Business Intelligence", "Communication"), List.of("Led analytics team"), List.of("Business analytics", "Interview readiness"), "Use data to tell a compelling story." )
        );

        for (AlumniSeed seed : alumniSeeds) {
            if (userRepository.existsByUsername(seed.username) || userRepository.existsByEmail(seed.email)) {
                continue;
            }

            User user = new User();
            user.setFullName(seed.fullName);
            user.setUsername(seed.username);
            user.setEmail(seed.email);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRole(Role.ALUMNI);

            Alumni alumni = new Alumni();
            alumni.setInstitutionName(seed.institutionName);
            alumni.setGraduationYear(seed.graduationYear);
            alumni.setIndustry(seed.industry);
            alumni.setCurrentCompany(seed.currentCompany);
            alumni.setAvailableForMentorship(true);
            alumni.setUser(user);

            alumni.setDetails(new com.example.alumniconfluxbackend.model.details.AlumniDetails());
            alumni.getDetails().setJobTitle(seed.jobTitle);
            alumni.getDetails().setExperienceLevel(seed.experienceLevel);
            alumni.getDetails().setSkills(seed.skills);
            alumni.getDetails().setAchievements(seed.achievements);
            alumni.getDetails().setCareerPath(seed.careerPath);
            alumni.getDetails().setAdvice(seed.advice);

            user.setAlumni(alumni);
            userRepository.save(user);
        }
    }

    private void createStudent() {
        String username = "student";
        String email = "student@alumniconflux.com";

        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
            return;
        }

        User user = new User();
        user.setFullName("Student One");
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRole(Role.STUDENT);

        Student student = new Student();
        student.setInstitutionName("University of Education");
        student.setExpectedGraduationYear((short) 2027);
        student.setUser(user);

        student.setDetails(new com.example.alumniconfluxbackend.model.details.StudentDetails());
        student.getDetails().setDepartment("Computer Science");
        student.getDetails().setDegreeProgram("BSc Computer Science");
        student.getDetails().setMajor("Artificial Intelligence");
        student.getDetails().setCurrentSemester((short) 5);
        student.getDetails().setSkills(List.of("Python", "SQL", "Data Analysis"));
        student.getDetails().setCareerPreferences(List.of("Mentorship", "Machine Learning", "Tech Careers"));

        user.setStudent(student);
        userRepository.save(user);
    }

    private static class AlumniSeed {
        private final String username;
        private final String email;
        private final String fullName;
        private final String institutionName;
        private final Short graduationYear;
        private final String industry;
        private final String currentCompany;
        private final String jobTitle;
        private final String experienceLevel;
        private final List<String> skills;
        private final List<String> achievements;
        private final List<String> careerPath;
        private final String advice;

        AlumniSeed(String username,
                   String email,
                   String fullName,
                   String institutionName,
                   Short graduationYear,
                   String industry,
                   String currentCompany,
                   String jobTitle,
                   String experienceLevel,
                   List<String> skills,
                   List<String> achievements,
                   List<String> careerPath,
                   String advice) {
            this.username = username;
            this.email = email;
            this.fullName = fullName;
            this.institutionName = institutionName;
            this.graduationYear = graduationYear;
            this.industry = industry;
            this.currentCompany = currentCompany;
            this.jobTitle = jobTitle;
            this.experienceLevel = experienceLevel;
            this.skills = skills;
            this.achievements = achievements;
            this.careerPath = careerPath;
            this.advice = advice;
        }
    }
}
