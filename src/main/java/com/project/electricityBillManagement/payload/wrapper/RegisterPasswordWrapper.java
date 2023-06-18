package com.project.electricityBillManagement.payload.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterPasswordWrapper {
    String email;

    String password;

    public RegisterPasswordWrapper(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
