package com.uekatowice.fraud.service;

import com.uekatowice.clients.fraud.FraudCheckResponse;
import com.uekatowice.fraud.entity.FraudCheckHistory;
import com.uekatowice.fraud.repository.FraudCheckHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FraudCheckService {

    private final FraudCheckHistoryRepository fraudCheckHistoryRepository;

    public FraudCheckResponse isFraudulentCustomer(Integer customerId) {
        boolean isCustomerFraudulent = validateIsCustomerFraudulent();
        FraudCheckHistory fraudCheckHistory = FraudCheckHistory.builder()
                .customerId(customerId)
                .isFraudster(isCustomerFraudulent)
                .createdAt(LocalDateTime.now())
                .build();

        fraudCheckHistoryRepository.save(fraudCheckHistory);
        return new FraudCheckResponse(isCustomerFraudulent);
    }

    private boolean validateIsCustomerFraudulent() {
        return false;
    }
}