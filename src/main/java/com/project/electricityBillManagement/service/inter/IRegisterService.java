package com.project.electricityBillManagement.service.inter;

import com.project.electricityBillManagement.model.Register;
import com.project.electricityBillManagement.payload.request.ForgotPasswordRequest;
import com.project.electricityBillManagement.payload.request.LoginRequest;
import com.project.electricityBillManagement.payload.wrapper.RegisterPasswordWrapper;
import com.project.electricityBillManagement.payload.wrapper.RegisterWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IRegisterService {

    Register signup(Register register);
    Register getByEmail(String email);
    Register getByPhoneNo(long phoneNo);
    String logIn(LoginRequest loginRequest);
    List<RegisterWrapper> getAllUser();

    String changePassword(LoginRequest loginRequest);

    String update(RegisterPasswordWrapper registerPasswordWrapper);
}
