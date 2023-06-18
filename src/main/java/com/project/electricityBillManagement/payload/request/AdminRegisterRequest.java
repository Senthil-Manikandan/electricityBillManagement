package com.project.electricityBillManagement.payload.request;

import com.project.electricityBillManagement.enumeration.AdminStatus;
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
public class AdminRegisterRequest {
    String firstName;
    String lastName;
    String email;
    long phoneNo;
    AdminStatus adminStatus;
}
