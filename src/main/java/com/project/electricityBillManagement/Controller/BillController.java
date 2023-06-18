package com.project.electricityBillManagement.Controller;

import com.project.electricityBillManagement.payload.request.BillRequest;
import com.project.electricityBillManagement.payload.request.EditBillRequest;
import com.project.electricityBillManagement.payload.request.PayBillRequest;
import com.project.electricityBillManagement.payload.request.PaymentRequest;
import com.project.electricityBillManagement.payload.wrapper.HistoryWrapper;
import com.project.electricityBillManagement.service.inter.IBillService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/bill")
@AllArgsConstructor
public class BillController {

    private final IBillService billService;

    @PostMapping("/createBill")
    public ResponseEntity<String> createBill(@RequestBody BillRequest request){
        try{
            String response = billService.createBill(request);
            if(response.equalsIgnoreCase("generated"))
                return new ResponseEntity<>(response, HttpStatus.OK);
            else
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/editBill")
    public ResponseEntity<String> editBill(@RequestBody EditBillRequest request){
        try{
            String response = billService.editBill(request);
            if(response.equalsIgnoreCase("generated"))
                return new ResponseEntity<>(response, HttpStatus.OK);
            else
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/payBill")
    public ResponseEntity<String> payBill(@RequestBody PayBillRequest request){
        try {
            String response = billService.payBill(request);
            if(response.equalsIgnoreCase("generated"))
                return new ResponseEntity<>(response, HttpStatus.OK);
            else
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/paymentHistory")
    public ResponseEntity<?> paymentHistory(@RequestBody PaymentRequest request){
        try {
            List<HistoryWrapper> response = billService.paymentHistory(request);
            if(response != null)
                return new ResponseEntity<>(response, HttpStatus.OK);
            else
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
