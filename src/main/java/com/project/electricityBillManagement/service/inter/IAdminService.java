package com.project.electricityBillManagement.service.inter;

import com.project.electricityBillManagement.payload.request.AdminRegisterRequest;
import com.project.electricityBillManagement.payload.request.ChangeConsumerStatusRequest;
import com.project.electricityBillManagement.payload.request.ConsumerRegisterRequest;
import com.project.electricityBillManagement.payload.wrapper.ConsumerWrapper;

import java.util.List;

public interface IAdminService {
    String createAdmin(AdminRegisterRequest request);

    String createConsumerByAdmin(ConsumerRegisterRequest request);

    List<ConsumerWrapper> getAllConsumer();

    String changeConsumerStatus(ChangeConsumerStatusRequest request);
}
