package com.project.electricityBillManagement.service.impl;

import com.project.electricityBillManagement.exception.UserNotFoundException;
import com.project.electricityBillManagement.model.Consumer;
import com.project.electricityBillManagement.repo.ConsumerRepository;
import com.project.electricityBillManagement.service.inter.IConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@Slf4j  //to print in console
public class ConsumerServiceImpl implements IConsumerService {

    private final ConsumerRepository consumerRepository;

    @Autowired
    public ConsumerServiceImpl(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
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
    public Consumer deleteConsumer(int consumerId) {
// amigos 2021 33:00
        return null;
    }

    @Override
    public Consumer getConsumerByEmail(String email) {
        return consumerRepository.findByEmail(email);
    }

    public List<Consumer> findAllConsumer(){
        return consumerRepository.findAll();
    }

}
