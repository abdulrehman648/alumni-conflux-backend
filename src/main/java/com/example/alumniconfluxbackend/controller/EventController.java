package com.example.alumniconfluxbackend.controller;

import com.example.alumniconfluxbackend.dto.request.EventRequest;
import com.example.alumniconfluxbackend.dto.response.EventRegistrationResponse;
import com.example.alumniconfluxbackend.dto.response.EventResponse;
import com.example.alumniconfluxbackend.facade.EventFacade;
import com.example.alumniconfluxbackend.util.EventStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventFacade eventFacade;

    public EventController(EventFacade eventFacade) {
        this.eventFacade = eventFacade;
    }

    @PostMapping("/request/{userId}")
    public ResponseEntity<EventResponse> requestEvent(
            @PathVariable Integer userId,
            @RequestBody EventRequest request) {
        return ResponseEntity.ok(eventFacade.createEventRequest(userId, request));
    }

    @PatchMapping("/{eventId}/status")
    public ResponseEntity<EventResponse> updateEventStatus(
            @PathVariable Integer eventId,
            @RequestParam EventStatus status) {
        return ResponseEntity.ok(eventFacade.updateEventStatus(eventId, status));
    }

    @PutMapping("/{eventId}/update/{userId}")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable Integer eventId,
            @PathVariable Integer userId,
            @RequestBody EventRequest request) {
        return ResponseEntity.ok(eventFacade.updateEvent(eventId, userId, request));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<EventResponse>> getPendingEventRequests() {
        return ResponseEntity.ok(eventFacade.getPendingEventRequests());
    }

    @GetMapping("/available/{userId}")
    public ResponseEntity<List<EventResponse>> getAvailableEvents(
            @PathVariable Integer userId) {
        return ResponseEntity.ok(eventFacade.getAvailableEvents(userId));
    }

    @PostMapping("/{eventId}/register/{userId}")
    public ResponseEntity<EventRegistrationResponse> registerForEvent(
            @PathVariable Integer eventId,
            @PathVariable Integer userId) {
        return ResponseEntity.ok(eventFacade.registerForEvent(userId, eventId));
    }

    @GetMapping("/creator/{userId}")
    public ResponseEntity<List<EventResponse>> getEventsCreatedByUser(
            @PathVariable Integer userId) {
        return ResponseEntity.ok(eventFacade.getEventsCreatedByUser(userId));
    }

    @GetMapping("/registered/{userId}")
    public ResponseEntity<List<EventRegistrationResponse>> getEventsRegisteredByUser(
            @PathVariable Integer userId) {
        return ResponseEntity.ok(eventFacade.getEventsRegisteredByUser(userId));
    }

    @GetMapping("/{eventId}/attendees/{userId}")
    public ResponseEntity<List<EventRegistrationResponse>> getAttendeesForEvent(
            @PathVariable Integer eventId,
            @PathVariable Integer userId) {
        return ResponseEntity.ok(eventFacade.getAttendeesForEvent(userId, eventId));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> getEventById(
            @PathVariable Integer eventId) {
        return ResponseEntity.ok(eventFacade.getEventById(eventId));
    }
}
