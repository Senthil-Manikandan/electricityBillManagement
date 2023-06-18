package com.project.electricityBillManagement.service.impl;

import com.project.electricityBillManagement.exception.CustomException;
import com.project.electricityBillManagement.exception.UserNotFoundException;
import com.project.electricityBillManagement.jwt.JwtService;
import com.project.electricityBillManagement.model.Register;
import com.project.electricityBillManagement.payload.request.ForgotPasswordRequest;
import com.project.electricityBillManagement.payload.request.LoginRequest;
import com.project.electricityBillManagement.payload.request.RegisterRequest;
import com.project.electricityBillManagement.payload.wrapper.AuthenticationResponse;
import com.project.electricityBillManagement.repo.RegisterRepository;
import com.project.electricityBillManagement.service.inter.IAuthenticationService;
import com.project.electricityBillManagement.utils.EmailUtlis;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final RegisterRepository registerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final EmailUtlis emailUtlis;
    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        var user = Register.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .phoneNo(request.getPhoneNo())
                .password(passwordEncoder.encode(request.getPassword()))
                .address(request.getAddress())
                .role(request.getRole())
                .email(request.getEmail())
                .build();
        var jwtToken = jwtService.generateToken(user);
        registerRepository.save(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
                  request.getEmail(),
                  request.getPassword()
          )
        );

        var user = registerRepository.findRegisterByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public String forgotPassword(ForgotPasswordRequest request) {
        try {
            var user = registerRepository.findRegisterByEmail(request.getEmail());
            if (user != null) {
                emailUtlis.forgotEmail("senthilmanikandan0780@gmail.com", "Forgot email EBS", "http://localhost:8080/electrcitybillmanagement/v1/auth/changePassword");
                return "Check your email for password update link";
            } else
                throw new UserNotFoundException("User Not found");
        }catch (Exception ex){
            throw new CustomException(ex.getMessage());
        }
    }

    @Override
    public String changePassword(LoginRequest request) {
        try{
            var user = registerRepository.findRegisterByEmail(request.getEmail());
            if (user != null) {
                Register register = registerRepository.findRegisterByEmail(request.getEmail());
                registerRepository.updatePassword(passwordEncoder.encode(request.getPassword()),register.getRegisterId());
                return "Update Successfully ";
            }
            else
                throw new UserNotFoundException("User Not found");
        }catch (Exception ex){
            return ex.getMessage();
        }
    }


}
