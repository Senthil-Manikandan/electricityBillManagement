package com.project.electricityBillManagement.service.inter;

import com.project.electricityBillManagement.model.Consumer;


public interface IConsumerService {
    Consumer createConsumer(Consumer consumer);
    Consumer updateConsumer(Consumer consumer);
    Consumer getConsumer(int consumerId);
    Consumer deleteConsumer(int consumerId);

    Consumer getConsumerByEmail(String email);

}
