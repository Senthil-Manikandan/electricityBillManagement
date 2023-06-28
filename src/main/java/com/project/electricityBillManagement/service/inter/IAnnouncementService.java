package com.project.electricityBillManagement.service.inter;

import com.project.electricityBillManagement.payload.request.AnnouncementRequest;
import com.project.electricityBillManagement.payload.request.EditAnnouncementRequest;
import com.project.electricityBillManagement.payload.wrapper.AnnouncementResponse;

import java.util.List;

public interface IAnnouncementService {
    String createAnnouncement(AnnouncementRequest request);

    String editAnnouncement(EditAnnouncementRequest request);

    String deleteAnnouncement(EditAnnouncementRequest request);

    List<AnnouncementResponse> getAnnouncement();
}
