package com.project.electricityBillManagement.service.inter;

import com.project.electricityBillManagement.model.Consumer;
import com.project.electricityBillManagement.payload.request.DashboardRequest;
import com.project.electricityBillManagement.payload.request.ForgotPasswordRequest;
import com.project.electricityBillManagement.payload.wrapper.ConsumerDashboard;


public interface IConsumerService {
    Consumer createConsumer(Consumer consumer);
    Consumer updateConsumer(Consumer consumer);
    Consumer getConsumer(int consumerId);

    Consumer getConsumer(String meterNo);

    Consumer deleteConsumer(int consumerId);

    Consumer getConsumerByEmail(String email);

    ConsumerDashboard generateConsumerDashboard(DashboardRequest request);
}
