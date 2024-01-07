package com.mindex.challenge.controller;

import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReportStructureController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportStructureController.class);

    @Autowired
    private ReportingStructureService reportingStructureService;

    @GetMapping("/reportingStructure/{id}")
    public ResponseEntity<Object> read(@PathVariable String id) {
        try {
            LOG.debug("Received reporting structure request for id [{}]", id);
            return ResponseEntity.ok(reportingStructureService.read(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}