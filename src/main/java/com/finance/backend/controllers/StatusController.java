package com.finance.backend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("status")
@Slf4j
public class StatusController {

    @GetMapping()
    public ResponseEntity<String> health() {
        log.info("Health check received");
        return ResponseEntity.ok("OK");
    }

}
