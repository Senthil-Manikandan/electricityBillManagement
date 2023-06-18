package com.project.electricityBillManagement.service.impl;

import com.project.electricityBillManagement.exception.UserNotFoundException;
import com.project.electricityBillManagement.jwt.JwtAuthenticationFilter;
import com.project.electricityBillManagement.model.Register;
import com.project.electricityBillManagement.payload.request.LoginRequest;
import com.project.electricityBillManagement.payload.wrapper.RegisterPasswordWrapper;
import com.project.electricityBillManagement.payload.wrapper.RegisterWrapper;
import com.project.electricityBillManagement.repo.RegisterRepository;
import com.project.electricityBillManagement.service.inter.IRegisterService;
import com.project.electricityBillManagement.utils.EmailUtlis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@Slf4j
public class RegisterServiceImpl implements IRegisterService {

    private final RegisterRepository registerRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailUtlis emailUtlis;

    @Autowired
    public RegisterServiceImpl(RegisterRepository registerRepository, PasswordEncoder passwordEncoder, EmailUtlis emailUtlis) {
        this.registerRepository = registerRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailUtlis = emailUtlis;
    }

    @Autowired
    AuthenticationManager authenticationManager;

//    @Autowired
//    JwtUtil jwtUtil;

    @Autowired
    JwtAuthenticationFilter jwtFilter;
//
//    @Autowired
//    CustomerUserDetailsService customerUserDetailsService;
    @Override
    public Register signup(Register register) {
        log.info("registered user : "+register.getUsername());
        Register user = registerRepository.findRegisterByEmail(register.getEmail());
        if(Objects.isNull(user)){
            return registerRepository.save(register);
        }else{
            throw new UserNotFoundException("user already exist ");
        }
    }



    @Override
    public Register getByEmail(String email) {
        return registerRepository.findRegisterByEmail(email);
    }

    @Override
    public Register getByPhoneNo(long phoneNo) {
        return registerRepository.findRegisterByPhoneNo(phoneNo);
    }


    @Override
    public String logIn(LoginRequest loginRequest) {
//        log.info("Inside login {}",loginRequest.getEmail());
//        try{
//            Authentication auth = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword())
//            );
//            if(auth.isAuthenticated()){
//                if(customerUserDetailsService.registerDetails().getEmail().equals(loginRequest.getEmail())){
//                    return jwtUtil.generateToken(customerUserDetailsService.registerDetails().getEmail(),
//                            customerUserDetailsService.registerDetails().getRole());
//                }
//            }
//        }catch(Exception ex){
//            log.error("{}",ex);
//            throw new UserNotFoundException(ex.getMessage());
//        }
        return null;
    }

    @Override
    public List<RegisterWrapper> getAllUser() {
        try{
            if(jwtFilter.IsAdmin()){
                return registerRepository.findAllByRegisterWrapper();
            }else{
//                log.info("{}",jwtFilter.IsAdmin());
                return new ArrayList<>();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public String changePassword(LoginRequest loginRequest) {
        try{
            Register register = registerRepository.findRegisterByEmail(loginRequest.getEmail());
            //register.setPassword(loginRequest.getPassword());

            registerRepository.updatePassword(passwordEncoder.encode(loginRequest.getPassword()),register.getRegisterId());
            return "Update Successfully ";
        }catch (Exception ex){
            ex.printStackTrace();
            return ex.getMessage();
        }
    }

    @Override
    public String update(RegisterPasswordWrapper registerPasswordWrapper) {
//        try{
//            if(jwtFilter.isConsumer() ||jwtFilter.isAdmin()){
//                Register registerDB = registerRepository.findRegisterByEmail(registerPasswordWrapper.getEmail());
//
//                registerRepository.updateEmail(registerPasswordWrapper.getPassword(), registerDB.getRegisterId());
//                return "Update Success";
//
//            }else{
//                return "UnAuthorized";
//            }
//
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }
        return "Something went wrong";
    }


}
