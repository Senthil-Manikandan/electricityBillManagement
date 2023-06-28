package com.project.electricityBillManagement.Controller;

import com.project.electricityBillManagement.model.Admin;
import com.project.electricityBillManagement.payload.request.AdminRegisterRequest;
import com.project.electricityBillManagement.payload.request.ChangeConsumerStatusRequest;
import com.project.electricityBillManagement.payload.request.ConsumerRegisterRequest;
import com.project.electricityBillManagement.payload.wrapper.ConsumerWrapper;
import com.project.electricityBillManagement.service.inter.IAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminRestController {

    private final IAdminService adminService;

    @PostMapping("/createAdmin")
    public ResponseEntity<String> createAdmin(@RequestBody AdminRegisterRequest request){
        try{
            String response = adminService.createAdmin(request);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createConsumer")
    public ResponseEntity<String> createConsumer(@RequestBody ConsumerRegisterRequest request){ //check for IsAdmin
        try{
            String response = adminService.createConsumerByAdmin(request);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllConsumer")
    public ResponseEntity<List<ConsumerWrapper>> getAllConsumer(){
        try{
            List<ConsumerWrapper> result = adminService.getAllConsumer();
            return new ResponseEntity<>(result,HttpStatus.OK);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<String> changeConsumerStatus(@RequestBody ChangeConsumerStatusRequest request){
        try{
           String response = adminService.changeConsumerStatus(request);
           return  new ResponseEntity<>(response,HttpStatus.OK);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
