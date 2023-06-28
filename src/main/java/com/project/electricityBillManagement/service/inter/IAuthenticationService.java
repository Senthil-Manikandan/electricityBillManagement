package com.project.electricityBillManagement.service.inter;

import com.project.electricityBillManagement.model.Consumer;
import com.project.electricityBillManagement.payload.request.ForgotPasswordRequest;
import com.project.electricityBillManagement.payload.request.LoginRequest;
import com.project.electricityBillManagement.payload.request.RegisterRequest;
import com.project.electricityBillManagement.payload.wrapper.AuthenticationResponse;
import com.project.electricityBillManagement.payload.wrapper.ResponseMessage;

import java.util.List;

public interface IAuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse login(LoginRequest request);

    ResponseMessage forgotPassword(ForgotPasswordRequest request);

    String changePassword(LoginRequest request);

    List<Consumer> getConsumers();

    ResponseMessage checkToken(String token);
}
