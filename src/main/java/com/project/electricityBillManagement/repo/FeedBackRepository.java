package com.project.electricityBillManagement.repo;

import com.project.electricityBillManagement.model.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface FeedBackRepository extends JpaRepository<FeedBack,Integer> {
    FeedBack findFeedBackByFeedbackId(int feedbackId);

    List<FeedBack> findFeedBacksByConsumerId(int consumerId);

    @Modifying
    @Query("UPDATE FeedBack f set f.review = ?1,f.consumerId = ?2 where f.feedbackId = ?3")
    int updateFeedback(String feedback, int consumerId, int feedbackId);

    void deleteFeedBackByFeedbackId(int feedbackId);
}
