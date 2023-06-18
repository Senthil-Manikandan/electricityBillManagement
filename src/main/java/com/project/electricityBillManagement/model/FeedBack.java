package com.project.electricityBillManagement.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "feedback")
public class FeedBack implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedbackId")
    int feedbackId;

    @NotNull
    @Column(name = "feedback")
    String review;

    @NotNull
    @Column(name = "consumerId")
    int consumerId;

    @Override
    public String toString() {
        return "FeedBack{" +
                "feedbackId=" + feedbackId +
                ", review='" + review + '\'' +
                ", consumerId=" + consumerId +
                '}';
    }
}
