package com.uekatowice.customer.service;

import com.uekatowice.clients.fraud.FraudCheckResponse;
import com.uekatowice.clients.fraud.FraudClient;
import com.uekatowice.clients.notification.NotificationRequest;
import com.uekatowice.customer.entity.Customer;
import com.uekatowice.customer.entity.dto.CustomerRegistrationDto;
import com.uekatowice.customer.repository.CustomerRepository;
import com.uekatowice.rabbitmq.RabbitMQMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer messageProducer;

    public void addNewCustomer(CustomerRegistrationDto customerRegistrationDto) {
        Customer customer = Customer.builder()
                .firstname(customerRegistrationDto.firstName())
                .lastname(customerRegistrationDto.lastName())
                .email(customerRegistrationDto.email())
                .build();

        customerRepository.saveAndFlush(customer);
        checkIsCustomerFraudster(customer);
        sendNotification(customer);
    }

    private void sendNotification(Customer customer) {
        String message = format("Hi %s, welcome to Application.", customer.getFirstname());
        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                message
        );
        messageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key");
    }

    private void checkIsCustomerFraudster(Customer customer) {
        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("Fraudster");
        }
    }
}