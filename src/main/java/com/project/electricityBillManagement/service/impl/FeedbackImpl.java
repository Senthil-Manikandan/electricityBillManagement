package com.project.electricityBillManagement.service.impl;

import com.project.electricityBillManagement.exception.CustomException;
import com.project.electricityBillManagement.exception.UserNotFoundException;
import com.project.electricityBillManagement.jwt.JwtAuthenticationFilter;
import com.project.electricityBillManagement.model.Admin;
import com.project.electricityBillManagement.model.Announcement;
import com.project.electricityBillManagement.model.Consumer;
import com.project.electricityBillManagement.model.FeedBack;
import com.project.electricityBillManagement.payload.request.EditFeedbackRequest;
import com.project.electricityBillManagement.payload.request.FeedbackRequest;
import com.project.electricityBillManagement.repo.ConsumerRepository;
import com.project.electricityBillManagement.repo.FeedBackRepository;
import com.project.electricityBillManagement.service.inter.IFeedbackService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class FeedbackImpl implements IFeedbackService {

    private final FeedBackRepository feedBackRepository;
    private final ConsumerRepository consumerRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    public String createFeedback(FeedbackRequest request) {
        try{
            if(!jwtAuthenticationFilter.IsAdmin()){
                String email = jwtAuthenticationFilter.getUserName();
                Consumer c = consumerRepository.findByEmail(email);
                FeedBack fb = FeedBack.builder()
                        .review(request.getFeedback())
                        .consumerId(c.getConsumerId()).build();
                feedBackRepository.save(fb);
                return "created successfully";
            }
            else
                return "not a consumer";
        }catch (Exception ex){
            ex.printStackTrace();
            throw new CustomException(ex.getMessage());
        }
    }

    @Override
    public String editFeedback(EditFeedbackRequest request) {
        try{
            if(!jwtAuthenticationFilter.IsAdmin()){
                String email = jwtAuthenticationFilter.getUserName();
                Consumer c = consumerRepository.findByEmail(email);
                FeedBack fb = feedBackRepository.findFeedBackByFeedbackId(request.getFeedbackId());
                if(c != null && fb != null){
                    int i = feedBackRepository.updateFeedback(request.getFeedback(),c.getConsumerId(),request.getFeedbackId());
                    if(i>0){
                        return "edit successful";
                    }else{
                        return "not able to update check feedback id";
                    }
                }else{
                    throw new UserNotFoundException("user id not found");
                }
            }else{
                return "not a consumer";
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw new CustomException(ex.getMessage());
        }
    }

    @Override
    public String deleteFeedback(EditFeedbackRequest request) {
        try{
            if(!jwtAuthenticationFilter.IsAdmin()){
                String email = jwtAuthenticationFilter.getUserName();
                Consumer c = consumerRepository.findByEmail(email);
                FeedBack fb = feedBackRepository.findFeedBackByFeedbackId(request.getFeedbackId());
                if(c != null && fb != null){
                    feedBackRepository.deleteFeedBackByFeedbackId(request.getFeedbackId());
                    return "deleted successfully";
                }else{
                    throw new UserNotFoundException("consumer not found");
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
    public List<FeedBack> getFeedBacks() {
        if(!jwtAuthenticationFilter.IsAdmin()){
            String email = jwtAuthenticationFilter.getUserName();
            Consumer c = consumerRepository.findByEmail(email);
            List<FeedBack> result = feedBackRepository.findFeedBacksByConsumerId(c.getConsumerId());
            return result;
        }else{
            return feedBackRepository.findAll();
        }
    }
}
