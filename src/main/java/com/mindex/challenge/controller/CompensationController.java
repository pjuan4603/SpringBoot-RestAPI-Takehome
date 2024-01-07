package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @PostMapping("/compensation")
    public ResponseEntity<Object> create(@RequestBody Compensation compensation) {
        try {
            LOG.debug("Received compensation create request for [{}]", compensation);
            return ResponseEntity.ok(compensationService.create(compensation));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/compensation/{id}")
    public ResponseEntity<Object> read(@PathVariable String id) {
        try {
            LOG.debug("Received employee create request for id [{}]", id);
            return ResponseEntity.ok(compensationService.read(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
