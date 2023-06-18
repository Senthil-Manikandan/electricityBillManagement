package com.project.electricityBillManagement.Controller;

import com.project.electricityBillManagement.model.Consumer;
import com.project.electricityBillManagement.service.impl.ConsumerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumer")
public class ConsumerRestController {
    private final ConsumerServiceImpl consumerService;

    public ConsumerRestController(ConsumerServiceImpl consumerService) {
        this.consumerService = consumerService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Consumer>> getAllConsumer(){
        List<Consumer> consumerList = consumerService.findAllConsumer();
        return new ResponseEntity<>(consumerList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Consumer> createConsumer(@RequestBody Consumer consumer){
        Consumer newConsumer = consumerService.createConsumer(consumer);
        return new ResponseEntity<>(newConsumer,HttpStatus.CREATED);
    }

//    Need to implemented
    @PutMapping("/update")
    public ResponseEntity<Consumer> updateConsumer(@RequestBody Consumer consumer){
        Consumer updateConsumer = consumerService.updateConsumer(consumer);
        return  new ResponseEntity<>(updateConsumer,HttpStatus.OK);
    }

}
