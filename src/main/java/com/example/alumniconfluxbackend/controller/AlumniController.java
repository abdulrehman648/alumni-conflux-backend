package com.example.alumniconfluxbackend.controller;

import com.example.alumniconfluxbackend.dto.request.AlumniRequest;
import com.example.alumniconfluxbackend.dto.response.AlumniResponse;
import com.example.alumniconfluxbackend.facade.AlumniFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/alumni")
public class AlumniController {
    private final AlumniFacade alumniFacade;

    public AlumniController(AlumniFacade alumniFacade) {
        this.alumniFacade = alumniFacade;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<AlumniResponse> create(
            @PathVariable Integer userId,
            @RequestBody AlumniRequest request) {

        return ResponseEntity.ok(alumniFacade.saveAlumni(userId, request));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<AlumniResponse> update(
            @PathVariable Integer userId,
            @RequestBody AlumniRequest request) {

        return ResponseEntity.ok(alumniFacade.saveAlumni(userId, request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<AlumniResponse> getAlumni(
            @PathVariable Integer userId) {

        return ResponseEntity.ok(alumniFacade.getAlumni(userId));
    }

    @GetMapping
    public ResponseEntity<java.util.List<AlumniResponse>> getAllAlumni() {
        return ResponseEntity.ok(alumniFacade.getAllAlumni());
    }

    @PostMapping(value = "/{userId}/profile-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AlumniResponse> uploadProfilePicture(
            @PathVariable Integer userId,
            @RequestPart("file") MultipartFile file) {

        return ResponseEntity.ok(alumniFacade.uploadProfilePicture(userId, file));
    }
}
