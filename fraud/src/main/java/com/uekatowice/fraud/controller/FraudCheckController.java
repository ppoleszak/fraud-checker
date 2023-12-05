package com.uekatowice.fraud.controller;

import com.uekatowice.clients.fraud.FraudCheckResponse;
import com.uekatowice.fraud.service.FraudCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/fraud-check")
public class FraudCheckController {
    private final FraudCheckService fraudCheckService;
    @GetMapping(path = "{customerId}")
    public ResponseEntity<FraudCheckResponse> isFraudster(@PathVariable("customerId") Integer customerId) {
        log.info("Customer fraud check for customer with id: {}", customerId);
        FraudCheckResponse fraudCheckResponse = fraudCheckService.isFraudulentCustomer(customerId);
        log.info("Customer with id {} fraud check response is: {}", customerId, fraudCheckResponse);

        return ResponseEntity.status(CREATED).body(fraudCheckResponse);
    }
}