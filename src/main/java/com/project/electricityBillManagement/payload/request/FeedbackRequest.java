package com.project.electricityBillManagement.payload.request;

import lombok.AllArgsConstructor;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequest {

    String feedback;
}
