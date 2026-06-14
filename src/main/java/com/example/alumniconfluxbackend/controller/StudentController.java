package com.example.alumniconfluxbackend.controller;

import com.example.alumniconfluxbackend.dto.response.StudentResponse;
import com.example.alumniconfluxbackend.facade.StudentFacade;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import com.example.alumniconfluxbackend.dto.request.StudentRequest;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final StudentFacade studentFacade;

    public StudentController(StudentFacade studentFacade) {
        this.studentFacade = studentFacade;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<StudentResponse> saveStudent(
            @PathVariable Integer userId,
            @RequestBody StudentRequest request) {

        return ResponseEntity.ok(studentFacade.saveStudent(userId, request));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<StudentResponse> update(
            @PathVariable Integer userId,
            @RequestBody StudentRequest request) {

        return ResponseEntity.ok(studentFacade.saveStudent(userId, request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<StudentResponse> getStudent(
            @PathVariable Integer userId) {

        return ResponseEntity.ok(studentFacade.getStudent(userId));
    }

    @PostMapping(value = "/{userId}/profile-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StudentResponse> uploadProfilePicture(
            @PathVariable Integer userId,
            @RequestPart("file") MultipartFile file) {

        return ResponseEntity.ok(studentFacade.uploadProfilePicture(userId, file));
    }
}
