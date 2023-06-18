package com.project.electricityBillManagement.service.inter;

import com.project.electricityBillManagement.payload.request.ForgotPasswordRequest;
import com.project.electricityBillManagement.payload.request.LoginRequest;
import com.project.electricityBillManagement.payload.request.RegisterRequest;
import com.project.electricityBillManagement.payload.wrapper.AuthenticationResponse;

public interface IAuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse login(LoginRequest request);

    String forgotPassword(ForgotPasswordRequest request);

    String changePassword(LoginRequest request);
}
