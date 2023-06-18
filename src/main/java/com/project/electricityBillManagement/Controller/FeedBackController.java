package com.project.electricityBillManagement.Controller;

import com.project.electricityBillManagement.payload.request.EditFeedbackRequest;
import com.project.electricityBillManagement.payload.request.FeedbackRequest;
import com.project.electricityBillManagement.service.inter.IFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback")
public class FeedBackController {

    private final IFeedbackService feedbackService;

    @PostMapping("/createFeedback")
    public ResponseEntity<String> createFeedback(@RequestBody FeedbackRequest request){
        try{
            String resposne = feedbackService.createFeedback(request);
            if(resposne.equalsIgnoreCase("created successfully"))
                return new ResponseEntity<>(resposne, HttpStatus.OK);
            else
                return new ResponseEntity<>(resposne, HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("editFeedback")
    public ResponseEntity<String> editFeedback(@RequestBody EditFeedbackRequest request){
        try{
            String resposne = feedbackService.editFeedback(request);
            if(resposne.equalsIgnoreCase("edit successful"))
                return new ResponseEntity<>(resposne, HttpStatus.OK);
            else
                return new ResponseEntity<>(resposne, HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteFeedback")
    public ResponseEntity<String> deleteFeedback(@RequestBody EditFeedbackRequest request){
        try{
            String resposne = feedbackService.deleteFeedback(request);
            if(resposne.equalsIgnoreCase("deleted successfully"))
                return new ResponseEntity<>(resposne, HttpStatus.OK);
            else
                return new ResponseEntity<>(resposne, HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
