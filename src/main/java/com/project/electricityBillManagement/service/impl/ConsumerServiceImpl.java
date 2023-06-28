package com.project.electricityBillManagement.service.impl;

import com.project.electricityBillManagement.enumeration.BillStatus;
import com.project.electricityBillManagement.enumeration.Status;
import com.project.electricityBillManagement.enumeration.Tariff;
import com.project.electricityBillManagement.exception.UserNotFoundException;
import com.project.electricityBillManagement.model.Bill;
import com.project.electricityBillManagement.model.Consumer;
import com.project.electricityBillManagement.payload.request.DashboardRequest;
import com.project.electricityBillManagement.payload.wrapper.ConsumerDashboard;
import com.project.electricityBillManagement.payload.wrapper.HistoryWrapper;
import com.project.electricityBillManagement.repo.BillRepository;
import com.project.electricityBillManagement.repo.ConsumerRepository;
import com.project.electricityBillManagement.service.inter.IConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
@Transactional
@Slf4j  //to print in console
public class ConsumerServiceImpl implements IConsumerService {

    private final ConsumerRepository consumerRepository;
    private final BillRepository billRepository;

    @Autowired
    public ConsumerServiceImpl(ConsumerRepository consumerRepository, BillRepository billRepository) {
        this.consumerRepository = consumerRepository;
        this.billRepository = billRepository;
    }

    @Override
    public Consumer createConsumer(Consumer consumer) {
        log.info("Saving Customer : {}",consumer.getEmail());
        return consumerRepository.save(consumer)  ;
    }

    @Override
    public Consumer updateConsumer(Consumer consumer) {
        return null;
    }

    @Override
    public Consumer getConsumer(int consumerId) {
        return consumerRepository.findByConsumerId(consumerId).orElseThrow(
                () -> new UserNotFoundException("User by id " + consumerId + " was not found")
        );
    }

    @Override
    public Consumer getConsumer(String meterNo) {
        return consumerRepository.findConsumerByMeterNo(meterNo).orElseThrow(
                () -> new UserNotFoundException("Meter No : " + meterNo + " was not found")
        );
    }

    @Override
    public Consumer deleteConsumer(int consumerId) {
// amigos 2021 33:00
        return null;
    }

    @Override
    public Consumer getConsumerByEmail(String email) {
        return consumerRepository.findByEmail(email);
    }

    @Override
    public ConsumerDashboard generateConsumerDashboard(DashboardRequest request) {
        Consumer c = getConsumerByEmail(request.getEmail());
        HistoryWrapper hw  = billRepository.findBillByConsumerIdAndFromDateAfterCustom(c.getConsumerId(),request.getDate());
        ConsumerDashboard cd =ConsumerDashboard.builder()
                .consumerStatus(c.getStatus())
                .tariff(c.getTariff())
                .meterNo(c.getMeterNo())
                .billStatus(hw.getStatus())
                .endDate(hw.getEndDate())
                .toDate(hw.getToDate())
                .fromDate(hw.getFromDate())
                .units(hw.getUnits())
                .totalAmount(hw.getTotalAmount())
                .paidAmount(hw.getPaidAmount())
                .build();
        return cd;
    }

    public List<Consumer> findAllConsumer(){
        return consumerRepository.findAll();
    }

}
