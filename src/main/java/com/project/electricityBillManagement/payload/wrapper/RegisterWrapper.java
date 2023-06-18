package com.project.electricityBillManagement.payload.wrapper;

import com.project.electricityBillManagement.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterWrapper {
    private int registerId;
    private String firstName;
    private String lastName;
    private String email;
    private long phonenNo;

    private Role role;

}
