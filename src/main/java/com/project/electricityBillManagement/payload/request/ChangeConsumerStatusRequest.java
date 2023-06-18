package com.project.electricityBillManagement.payload.request;

import com.project.electricityBillManagement.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeConsumerStatusRequest {

    Status status;
    String email;

}
