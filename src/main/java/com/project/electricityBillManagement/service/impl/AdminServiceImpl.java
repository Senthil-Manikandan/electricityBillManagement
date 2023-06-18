package com.project.electricityBillManagement.service.impl;

import com.project.electricityBillManagement.exception.CustomException;
import com.project.electricityBillManagement.exception.UserNotFoundException;
import com.project.electricityBillManagement.model.Admin;
import com.project.electricityBillManagement.model.Consumer;
import com.project.electricityBillManagement.payload.request.AdminRegisterRequest;
import com.project.electricityBillManagement.payload.request.ChangeConsumerStatusRequest;
import com.project.electricityBillManagement.payload.request.ConsumerRegisterRequest;
import com.project.electricityBillManagement.payload.wrapper.ConsumerWrapper;
import com.project.electricityBillManagement.repo.AdminRepository;
import com.project.electricityBillManagement.repo.ConsumerRepository;
import com.project.electricityBillManagement.service.inter.IAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements IAdminService {

    private final AdminRepository adminRepository;
    private final ConsumerRepository consumerRepository;
    @Override
    public String createAdmin(AdminRegisterRequest request) {
        try{
            Admin admin = Admin.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .phoneNo(request.getPhoneNo())
                    .adminStatus(request.getAdminStatus())
                    .build();
            adminRepository.save(admin);
            return "Created Successfully";
        }catch (Exception ex){
            ex.printStackTrace();
            throw new CustomException(ex.getMessage());
        }
    }

    @Override
    public String createConsumerByAdmin(ConsumerRegisterRequest request) {
        try{
            Consumer consumer = Consumer.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .phoneNo(request.getPhoneNo())
                    .status(request.getStatus())
                    .tariff(request.getTariff())
                    .meterNo(request.getMeterNo())
                    .address(request.getAddress())
                    .build();
            consumerRepository.save(consumer);
            return "Created Successfully";
        }catch (Exception ex){
            ex.printStackTrace();
            throw new CustomException(ex.getMessage());
        }
    }

    @Override
    public List<ConsumerWrapper> getAllConsumer() {
        try{
            List<ConsumerWrapper> result = consumerRepository.findAllByWrapper();
            return result;
        }catch (Exception ex){
            ex.printStackTrace();
            throw new CustomException(ex.getMessage());
        }
    }

    @Override
    public String changeConsumerStatus(ChangeConsumerStatusRequest request) {
        try{
            Consumer c = consumerRepository.findByEmail(request.getEmail());
            consumerRepository.changeStatus(request.getStatus(),c.getConsumerId());
            c = consumerRepository.findByEmail(request.getEmail());
            return c.toString();
        }catch (Exception ex){
            ex.printStackTrace();
            throw  new UserNotFoundException(ex.getMessage());
        }
    }
}
