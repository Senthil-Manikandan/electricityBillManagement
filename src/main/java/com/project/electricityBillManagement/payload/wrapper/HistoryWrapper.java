package com.project.electricityBillManagement.payload.wrapper;

import com.project.electricityBillManagement.enumeration.BillStatus;
import lombok.*;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryWrapper {
    int billNo;
    double arrears;
    double totalAmount;
    double paidAmount;
    double units;
    Date fromDate;
    Date toDate;
    Date endDate;
    BillStatus status;

    int adminId;

}
