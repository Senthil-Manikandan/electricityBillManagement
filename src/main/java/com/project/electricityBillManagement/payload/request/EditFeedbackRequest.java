package com.project.electricityBillManagement.payload.request;

import lombok.*;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditFeedbackRequest {
    int feedbackId;
    int consumerId;
    String feedback;
}
