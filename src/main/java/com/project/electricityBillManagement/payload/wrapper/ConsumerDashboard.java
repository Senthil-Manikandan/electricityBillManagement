package com.project.electricityBillManagement.payload.wrapper;

import com.project.electricityBillManagement.enumeration.BillStatus;
import com.project.electricityBillManagement.enumeration.Status;
import com.project.electricityBillManagement.enumeration.Tariff;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsumerDashboard {
    double totalAmount;
    double paidAmount;
    double units;
    Date fromDate;
    Date toDate;
    Date endDate;
    BillStatus billStatus;

    String meterNo;

    Tariff tariff;

    Status consumerStatus;

}
