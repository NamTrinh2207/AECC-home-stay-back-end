package com.example.homestay.controller;

import com.example.homestay.model.CancelRequest;
import com.example.homestay.service.cancelRequest.ICancelRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/cancel")
public class CancelRequestController {
    @Autowired
    private ICancelRequestService cancelRequestService;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<Iterable<CancelRequest>> cancelRequests() {
        Iterable<CancelRequest> cancelRequests = cancelRequestService.findAll();
        return new ResponseEntity<>(cancelRequests, HttpStatus.OK);
    }

    @PostMapping("/new-request")
    public ResponseEntity<CancelRequest> sendNewRequest(@RequestBody CancelRequest cancelRequest) {
        return new ResponseEntity<>(cancelRequestService.save(cancelRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> viewRequest(@PathVariable Long id) {
        Optional<CancelRequest> cancelRequestOptional = cancelRequestService.findById(id);
        return cancelRequestOptional
                .map(cancelRequest -> new ResponseEntity<>(cancelRequest, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
