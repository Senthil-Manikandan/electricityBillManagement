package com.project.electricityBillManagement.service.inter;

import com.project.electricityBillManagement.payload.request.EditFeedbackRequest;
import com.project.electricityBillManagement.payload.request.FeedbackRequest;

public interface IFeedbackService {
    String createFeedback(FeedbackRequest request);

    String editFeedback(EditFeedbackRequest request);

    String deleteFeedback(EditFeedbackRequest request);
}
