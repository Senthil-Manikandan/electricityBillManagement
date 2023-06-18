package com.project.electricityBillManagement.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditAnnouncementRequest {
    int announcementId;
    int adminId;
    String announcement;
}
