package com.project.electricityBillManagement.Controller;

import com.project.electricityBillManagement.payload.request.AnnouncementRequest;
import com.project.electricityBillManagement.payload.request.EditAnnouncementRequest;
import com.project.electricityBillManagement.service.inter.IAnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/announcement")
public class AnnocementRestController {
    private final IAnnouncementService announcementService;

    @PostMapping("/createAnnouncement")
    public ResponseEntity<String> createAnnouncement(@RequestBody AnnouncementRequest request){
        try{
            String response = announcementService.createAnnouncement(request);
            if (response.equalsIgnoreCase("created successfully"))
                return new ResponseEntity<>(response,HttpStatus.OK);
            else
                return  new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            return  new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/editAnnouncement")
    public ResponseEntity<String> editAnnouncement(@RequestBody EditAnnouncementRequest request){
        try{
            String response = announcementService.editAnnouncement(request);
            if (response.equalsIgnoreCase("update successful"))
                return new ResponseEntity<>(response,HttpStatus.OK);
            else
                return  new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            return  new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAnnouncement")
    public ResponseEntity<String> deleteAnnouncement(@RequestBody EditAnnouncementRequest request){
        try{
            String response = announcementService.deleteAnnouncement(request);
            if (response.equalsIgnoreCase("delete successful"))
                return new ResponseEntity<>(response,HttpStatus.OK);
            else
                return  new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            return  new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
