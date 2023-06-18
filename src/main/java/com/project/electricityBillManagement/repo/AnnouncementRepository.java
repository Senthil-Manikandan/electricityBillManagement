package com.project.electricityBillManagement.repo;

import com.project.electricityBillManagement.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional

public interface AnnouncementRepository extends JpaRepository<Announcement,Integer> {
    @Modifying
    @Query("UPDATE Announcement a SET a.review = ?1,a.adminId = ?2 where a.announcementId = ?3")
    int updateRecord(@Param("announcement")String announcement,@Param("adminId") int adminId,@Param("announcementId") int announcementId);

    Announcement findAnnouncementByAnnouncementId(int AnnouncementId);

    @Modifying
    @Query("DELETE FROM Announcement a where a.announcementId = ?1")
    void deleteAnnouncements(int AnnouncementId);
}
