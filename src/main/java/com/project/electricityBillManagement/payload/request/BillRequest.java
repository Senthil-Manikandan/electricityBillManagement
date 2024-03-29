package com.project.electricityBillManagement.payload.request;

import com.project.electricityBillManagement.enumeration.BillStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillRequest {
    double units;
    Date fromDate;
    Date toDate;
    Date endDate;
    BillStatus status;

    String meterNo;
}
