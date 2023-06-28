package com.project.electricityBillManagement.service.impl;

import com.project.electricityBillManagement.exception.CustomException;
import com.project.electricityBillManagement.exception.UserNotFoundException;
import com.project.electricityBillManagement.jwt.JwtAuthenticationFilter;
import com.project.electricityBillManagement.jwt.JwtService;
import com.project.electricityBillManagement.model.Consumer;
import com.project.electricityBillManagement.model.Register;
import com.project.electricityBillManagement.payload.request.ForgotPasswordRequest;
import com.project.electricityBillManagement.payload.request.LoginRequest;
import com.project.electricityBillManagement.payload.request.RegisterRequest;
import com.project.electricityBillManagement.payload.wrapper.AuthenticationResponse;
import com.project.electricityBillManagement.payload.wrapper.ResponseMessage;
import com.project.electricityBillManagement.repo.ConsumerRepository;
import com.project.electricityBillManagement.repo.RegisterRepository;
import com.project.electricityBillManagement.service.inter.IAuthenticationService;
import com.project.electricityBillManagement.utils.EmailUtlis;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final RegisterRepository registerRepository;
    private final ConsumerRepository consumerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JwtAuthenticationFilter filter;
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
        return AuthenticationResponse.builder().token(jwtToken).message("Signup Successful").build();
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
        return AuthenticationResponse.builder().token(jwtToken).message("login successful").build();
    }

    @Override
    public ResponseMessage forgotPassword(ForgotPasswordRequest request) {
        try {
            var user = registerRepository.findRegisterByEmail(request.getEmail());
            if (user != null) {
                emailUtlis.forgotEmail("senthilmanikandan0780@gmail.com", "Forgot email EBS", "http://localhost:8080/electrcitybillmanagement/v1/auth/changePassword");
                return ResponseMessage.builder().message("Check your Email").build();
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
                String tokenUserName = filter.getUserName();
                if(tokenUserName.equals(request.getEmail())){
                    Register register = registerRepository.findRegisterByEmail(request.getEmail());
                    registerRepository.updatePassword(passwordEncoder.encode(request.getPassword()),register.getRegisterId());
                    return "Updated Successfully";
                }else{
                    return "Email Does not match";
                }

            }
            else
                throw new UserNotFoundException("User Not found");
        }catch (Exception ex){
            return ex.getMessage();
        }
    }

    @Override
    public List<Consumer> getConsumers() {
        return consumerRepository.findAll();
    }

    @Override
    public ResponseMessage checkToken(String token) {
        try{
            ResponseMessage r;
            if(filter.checkTokenValid(token)){
                r = ResponseMessage.builder().message("true").build();
            }else{
                r = ResponseMessage.builder().message("false").build();
            }
            return r;
        }catch(Exception ex){
            ex.printStackTrace();
            ResponseMessage r = ResponseMessage.builder().message(ex.getMessage()).build();
            return  r;
        }
    }


}
