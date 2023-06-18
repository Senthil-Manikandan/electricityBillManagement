package com.project.electricityBillManagement.payload.request;

import com.project.electricityBillManagement.enumeration.Status;
import com.project.electricityBillManagement.enumeration.Tariff;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsumerRegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private long phoneNo;
    private String address;
    private String meterNo;
    private Tariff tariff;
    private Status status;

}
