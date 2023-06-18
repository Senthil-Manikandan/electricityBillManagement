package com.project.electricityBillManagement.Controller;

import com.project.electricityBillManagement.payload.request.*;
import com.project.electricityBillManagement.payload.wrapper.HistoryWrapper;
import com.project.electricityBillManagement.service.inter.IBillService;
import com.project.electricityBillManagement.utils.BillPDFExporter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @PostMapping("/generateBill")
    public ResponseEntity<?> generateBill(@RequestBody GenerateBillRequest request, HttpServletResponse response){
        try {
            response.setContentType("application/pdf");
            DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
            String currentDateTime = dateFormat.format(new Date());
            String headerkey = "Content-Disposition";
            String headervalue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
            response.setHeader(headerkey, headervalue);
            List<HistoryWrapper> result = billService.generateReport(request);
            BillPDFExporter generator = new BillPDFExporter();
            generator.generate(result,response);
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
