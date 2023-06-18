package com.project.electricityBillManagement.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayBillRequest {
    int billNo;
    int customerNo;

    double paidAmt;
}
