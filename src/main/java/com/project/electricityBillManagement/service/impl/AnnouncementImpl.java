package com.project.electricityBillManagement.service.impl;

import com.project.electricityBillManagement.exception.CustomException;
import com.project.electricityBillManagement.exception.UserNotFoundException;
import com.project.electricityBillManagement.jwt.JwtAuthenticationFilter;
import com.project.electricityBillManagement.model.Admin;
import com.project.electricityBillManagement.model.Announcement;
import com.project.electricityBillManagement.payload.request.AnnouncementRequest;
import com.project.electricityBillManagement.payload.request.EditAnnouncementRequest;
import com.project.electricityBillManagement.repo.AdminRepository;
import com.project.electricityBillManagement.repo.AnnouncementRepository;
import com.project.electricityBillManagement.service.inter.IAnnouncementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnnouncementImpl implements IAnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final AdminRepository adminRepository;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Override
    public String createAnnouncement(AnnouncementRequest request) {
        try{
            if(jwtAuthenticationFilter.IsAdmin()){
                Admin ad = adminRepository.findAdminByAdminId(request.getAdminId());
                if(ad != null){
                    Announcement ann = Announcement.builder()
                            .review(request.getAnnouncement())
                            .adminId(request.getAdminId()).build();
                    announcementRepository.save(ann);
                    return "created successfully";
                }else{
                    throw new UserNotFoundException("admin not found");
                }
            }else{
                return "Not a admin";
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw  new CustomException(ex.getMessage());
        }
    }

    @Override
    public String editAnnouncement(EditAnnouncementRequest request) {
        try{
            if(jwtAuthenticationFilter.IsAdmin()){
                Admin ad = adminRepository.findAdminByAdminId(request.getAdminId());
                Announcement an = announcementRepository.findAnnouncementByAnnouncementId(request.getAnnouncementId());
                if(ad != null && an != null){
                    int i = announcementRepository.updateRecord(request.getAnnouncement(),request.getAdminId(),request.getAnnouncementId());
                    if(i>0)
                        return "update successful";
                    else
                        throw new CustomException("error during edit");
                }else{
                    throw new UserNotFoundException("admin not found");
                }
            }else{
                return "Not a admin";
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw  new CustomException(ex.getMessage());
        }
    }

    @Override
    public String deleteAnnouncement(EditAnnouncementRequest request) {
        try{
            if(jwtAuthenticationFilter.IsAdmin()){
                Admin ad = adminRepository.findAdminByAdminId(request.getAdminId());
                Announcement an = announcementRepository.findAnnouncementByAnnouncementId(request.getAnnouncementId());
                if(ad != null && an != null){
                    announcementRepository.deleteAnnouncements(request.getAnnouncementId());
                    return "delete successful";
                }else{
                    throw new UserNotFoundException("admin not found");
                }
            }else{
                return "Not a admin";
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw  new CustomException(ex.getMessage());
        }
    }
}
