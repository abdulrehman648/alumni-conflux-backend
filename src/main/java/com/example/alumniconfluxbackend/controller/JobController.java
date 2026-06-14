package com.example.alumniconfluxbackend.controller;

import com.example.alumniconfluxbackend.dto.request.JobApplicationRequest;
import com.example.alumniconfluxbackend.dto.request.JobRequest;
import com.example.alumniconfluxbackend.dto.response.JobApplicationResponse;
import com.example.alumniconfluxbackend.dto.response.JobResponse;
import com.example.alumniconfluxbackend.facade.JobFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobFacade jobFacade;

    public JobController(JobFacade jobFacade) {
        this.jobFacade = jobFacade;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<JobResponse> createJob(
            @PathVariable Integer userId,
            @RequestBody JobRequest request) {
        return ResponseEntity.ok(jobFacade.createJob(userId, request));
    }

    @PutMapping("/{userId}/{jobId}")
    public ResponseEntity<JobResponse> updateJob(
            @PathVariable Integer userId,
            @PathVariable Integer jobId,
            @RequestBody JobRequest request) {
        return ResponseEntity.ok(jobFacade.updateJob(userId, jobId, request));
    }

    @DeleteMapping("/{userId}/{jobId}")
    public ResponseEntity<Void> deleteJob(
            @PathVariable Integer userId,
            @PathVariable Integer jobId) {
        jobFacade.deleteJob(userId, jobId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobResponse> getJobById(
            @PathVariable Integer jobId) {
        return ResponseEntity.ok(jobFacade.getJobById(jobId));
    }

    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        return ResponseEntity.ok(jobFacade.getAllJobs());
    }

    @GetMapping("/alumni/{userId}")
    public ResponseEntity<List<JobResponse>> getJobsByAlumni(
            @PathVariable Integer userId) {
        return ResponseEntity.ok(jobFacade.getJobsByAlumni(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobResponse>> searchJobsByTitle(
            @RequestParam String title) {
        return ResponseEntity.ok(jobFacade.searchJobsByTitle(title));
    }

    @PostMapping("/{jobId}/apply/{userId}")
    public ResponseEntity<JobApplicationResponse> applyForJob(
            @PathVariable Integer jobId,
            @PathVariable Integer userId,
            @RequestBody JobApplicationRequest request) {
        return ResponseEntity.ok(jobFacade.applyForJob(userId, jobId, request));
    }

    @GetMapping("/{jobId}/applications/{userId}")
    public ResponseEntity<List<JobApplicationResponse>> getApplicationsForJob(
            @PathVariable Integer jobId,
            @PathVariable Integer userId) {
        return ResponseEntity.ok(jobFacade.getApplicationsForJob(userId, jobId));
    }

    @GetMapping("/applications/my/{userId}")
    public ResponseEntity<List<JobApplicationResponse>> getMyApplications(
            @PathVariable Integer userId) {
        return ResponseEntity.ok(jobFacade.getMyApplications(userId));
    }
}
