package com.project.electricityBillManagement.Controller;

import com.project.electricityBillManagement.payload.request.AnnouncementRequest;
import com.project.electricityBillManagement.payload.request.EditAnnouncementRequest;
import com.project.electricityBillManagement.payload.wrapper.AnnouncementResponse;
import com.project.electricityBillManagement.payload.wrapper.ResponseMessage;
import com.project.electricityBillManagement.service.inter.IAnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/announcement")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AnnocementRestController {
    private final IAnnouncementService announcementService;

    @PostMapping("/createAnnouncement")
    public ResponseEntity<ResponseMessage> createAnnouncement(@RequestBody AnnouncementRequest request){
        try{
            String response = announcementService.createAnnouncement(request);
            ResponseMessage r  = ResponseMessage.builder().message(response).build();
            if (response.equalsIgnoreCase("created successfully"))
                return new ResponseEntity<>(r,HttpStatus.OK);
            else
                return  new ResponseEntity<>(r,HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ResponseMessage r  = ResponseMessage.builder().message(ex.getMessage()).build();
            return  new ResponseEntity<>(r, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/editAnnouncement")
    public ResponseEntity<ResponseMessage> editAnnouncement(@RequestBody EditAnnouncementRequest request){
        try{
            String response = announcementService.editAnnouncement(request);
            ResponseMessage r  = ResponseMessage.builder().message(response).build();
            if (response.equalsIgnoreCase("update successful"))
                return new ResponseEntity<>(r,HttpStatus.OK);
            else
                return  new ResponseEntity<>(r,HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ResponseMessage r  = ResponseMessage.builder().message(ex.getMessage()).build();
            return  new ResponseEntity<>(r, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAnnouncement")
    public ResponseEntity<ResponseMessage> deleteAnnouncement(@RequestBody EditAnnouncementRequest request){
        try{
            String response = announcementService.deleteAnnouncement(request);
            ResponseMessage r  = ResponseMessage.builder().message(response).build();
            if (response.equalsIgnoreCase("delete successful"))
                return new ResponseEntity<>(r,HttpStatus.OK);
            else
                return  new ResponseEntity<>(r,HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ResponseMessage r  = ResponseMessage.builder().message(ex.getMessage()).build();
            return  new ResponseEntity<>(r, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAnnouncement")
    public ResponseEntity<List<AnnouncementResponse>> getAnnouncement(){
        try{
            List<AnnouncementResponse> result = announcementService.getAnnouncement();
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
        catch(Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
