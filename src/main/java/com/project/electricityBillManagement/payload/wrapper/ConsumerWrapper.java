package com.project.electricityBillManagement.payload.wrapper;

import com.project.electricityBillManagement.enumeration.Status;
import com.project.electricityBillManagement.enumeration.Tariff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerWrapper {

    private String firstName;
    private String lastName;
    private String email;
    private long phoneNo;
    private String address;
    private String meterNo;
    private Tariff tariff;
    private Status status;
}
