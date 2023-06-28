package com.project.electricityBillManagement.payload.request;

import com.project.electricityBillManagement.enumeration.BillStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillStatusRequest {
    BillStatus billStatus;
}
