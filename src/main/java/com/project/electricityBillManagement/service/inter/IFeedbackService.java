package com.project.electricityBillManagement.service.inter;

import com.project.electricityBillManagement.model.FeedBack;
import com.project.electricityBillManagement.payload.request.EditFeedbackRequest;
import com.project.electricityBillManagement.payload.request.FeedbackRequest;

import java.util.List;

public interface IFeedbackService {
    String createFeedback(FeedbackRequest request);

    String editFeedback(EditFeedbackRequest request);

    String deleteFeedback(EditFeedbackRequest request);

    List<FeedBack> getFeedBacks();
}
