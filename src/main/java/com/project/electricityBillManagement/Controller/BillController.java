package com.project.electricityBillManagement.Controller;

import com.project.electricityBillManagement.model.Bill;
import com.project.electricityBillManagement.payload.request.*;
import com.project.electricityBillManagement.payload.wrapper.HistoryWrapper;
import com.project.electricityBillManagement.payload.wrapper.ResponseMessage;
import com.project.electricityBillManagement.service.inter.IBillService;
import com.project.electricityBillManagement.utils.BillPDFExporter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/bill")
@AllArgsConstructor
public class BillController {

    private final IBillService billService;

    @PostMapping("/createBill")
    public ResponseEntity<ResponseMessage> createBill(@RequestBody BillRequest request){
        try{
            String response = billService.createBill(request);
            ResponseMessage r = ResponseMessage.builder().message(response).build();
            if(response.equalsIgnoreCase("generated"))
                return new ResponseEntity<>(r, HttpStatus.OK);
            else
                return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ResponseMessage r = ResponseMessage.builder().message(ex.getMessage()).build();
            return new ResponseEntity<>(r, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/editBill")
    public ResponseEntity<ResponseMessage> editBill(@RequestBody EditBillRequest request){
        try{
            String response = billService.editBill(request);
            ResponseMessage r = ResponseMessage.builder().message(response).build();
            if(response.equalsIgnoreCase("generated"))
                return new ResponseEntity<>(r, HttpStatus.OK);
            else
                return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ResponseMessage r = ResponseMessage.builder().message(ex.getMessage()).build();
            return new ResponseEntity<>(r, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/payBill")
    public ResponseEntity<ResponseMessage> payBill(@RequestBody PayBillRequest request){
        try {
            String response = billService.payBill(request);
            ResponseMessage r = ResponseMessage.builder().message(response).build();
            if(response.equalsIgnoreCase("generated"))
                return new ResponseEntity<>(r, HttpStatus.OK);
            else
                return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ResponseMessage r = ResponseMessage.builder().message(ex.getMessage()).build();
            return new ResponseEntity<>(r, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getBill")
    public ResponseEntity<List<Map<String, Object>>> getBill(){
        try{
            List<Bill> response = billService.getBill();
           // Replace with your actual logic to fetch the list of Bill objects

            List<Map<String, Object>> billDetails = new ArrayList<>();

            for (Bill bill : response) {
                Map<String, Object> billMap = new HashMap<>();
                billMap.put("billNo", bill.getBillNo());
                billMap.put("arrears", bill.getArrears());
                billMap.put("units", bill.getUnits());
                billMap.put("fromDate", bill.getFromDate());
                billMap.put("toDate", bill.getToDate());
                billMap.put("endDate", bill.getEndDate());
                billMap.put("status", bill.getStatus());
                billMap.put("totalAmount", bill.getTotalAmount());
                billMap.put("paidAmount", bill.getPaidAmount());
                billMap.put("consumerId", bill.getConsumerId());
                billMap.put("adminId", bill.getAdminId());

                billMap.put("Billdetails", bill.toString()); // Add the toString representation of the Bill object

                billDetails.add(billMap);
            }
            if(response != null)
                return new ResponseEntity<>(billDetails,HttpStatus.OK);
            else
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getBillByBillNo")
    public ResponseEntity<Bill> getBillByBillNo(@RequestBody DeleteBillRequest request){
        try{
            Bill response = billService.getBillByBillNo(request);
            if(response != null)
                return new ResponseEntity<>(response,HttpStatus.OK);
            else
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/paymentHistory")
    public ResponseEntity<?> paymentHistory(){
        try {
            List<HistoryWrapper> response = billService.paymentHistory();
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

    @DeleteMapping("/deleteBill")
    public ResponseEntity<ResponseMessage> deleteBill(@RequestBody DeleteBillRequest request){
        try{
            String response = billService.deleteBill(request);
            ResponseMessage r = ResponseMessage.builder().message(response).build();
            if(response.equalsIgnoreCase("Delete Successfully"))
                return new ResponseEntity<>(r,HttpStatus.OK);
            else
                return new ResponseEntity<>(r,HttpStatus.BAD_REQUEST);
        }catch(Exception ex){
            ResponseMessage r = ResponseMessage.builder().message(ex.getMessage()).build();
            return new ResponseEntity<>(r,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getBillByStatusAdmin")
    public ResponseEntity<List<Bill>> getBillbyStatusAdmin(@RequestBody BillStatusRequest request){
       try{
           List<Bill>  response = billService.getBillbyStatusAdmin(request);
           return new ResponseEntity<>(response,HttpStatus.OK);
       }catch (Exception ex){
           ex.printStackTrace();
           return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @PostMapping("/getBillByStatusConsumer")
    public ResponseEntity<List<Bill>> getBillbyStatusConsumer(@RequestBody BillStatusRequest request){
        try{
            List<Bill>  response = billService.getBillbyStatusConsumer(request);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
