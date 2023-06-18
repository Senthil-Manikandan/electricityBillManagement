package com.project.electricityBillManagement.Controller;

import com.project.electricityBillManagement.payload.request.ForgotPasswordRequest;
import com.project.electricityBillManagement.payload.request.LoginRequest;
import com.project.electricityBillManagement.payload.request.RegisterRequest;
import com.project.electricityBillManagement.payload.wrapper.AuthenticationResponse;
import com.project.electricityBillManagement.service.inter.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/electrcitybillmanagement/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return new ResponseEntity<>( authenticationService.register(request), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request){
        return new ResponseEntity<>( authenticationService.login(request), HttpStatus.OK);
    }

    @PostMapping("/forgotpassword")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest){
        try {
            return new ResponseEntity<>(authenticationService.forgotPassword(forgotPasswordRequest), HttpStatus.OK);
        }catch (Exception ex){
            return  new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody LoginRequest request){
        try {
            return new ResponseEntity<>(authenticationService.changePassword(request), HttpStatus.OK);
        }catch (Exception ex){
            return  new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
