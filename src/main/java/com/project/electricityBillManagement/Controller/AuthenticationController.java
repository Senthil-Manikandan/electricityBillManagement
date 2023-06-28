package com.project.electricityBillManagement.Controller;

import com.project.electricityBillManagement.model.Consumer;
import com.project.electricityBillManagement.payload.request.ForgotPasswordRequest;
import com.project.electricityBillManagement.payload.request.LoginRequest;
import com.project.electricityBillManagement.payload.request.RegisterRequest;
import com.project.electricityBillManagement.payload.request.TokenRequest;
import com.project.electricityBillManagement.payload.wrapper.AuthenticationResponse;
import com.project.electricityBillManagement.payload.wrapper.ResponseMessage;
import com.project.electricityBillManagement.service.inter.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/electrcitybillmanagement/v1/auth")
@RequiredArgsConstructor
@Slf4j
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
    public ResponseEntity<ResponseMessage> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest){
        try {
            return new ResponseEntity<>(authenticationService.forgotPassword(forgotPasswordRequest), HttpStatus.OK);
        }catch (Exception ex){
            var result =  ResponseMessage.builder().message(ex.getMessage()).build();
            return  new ResponseEntity<>(result,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<ResponseMessage> changePassword(@RequestBody LoginRequest request){
        try {
            String response= authenticationService.changePassword(request);
            ResponseMessage r = ResponseMessage.builder().message(response).build();
            if(response.equalsIgnoreCase("Updated Successfully"))
                return new ResponseEntity<>(r, HttpStatus.OK);
            else
                return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ResponseMessage r = ResponseMessage.builder().message(ex.getMessage()).build();
            return  new ResponseEntity<>(r,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getConsumers")
    public ResponseEntity<List<Consumer>> getConsumers(){
        try {
            return new ResponseEntity<>(authenticationService.getConsumers(), HttpStatus.OK);
        }catch (Exception ex){
            return  new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/user/checktoken")
    public ResponseEntity<ResponseMessage> checkToken(@RequestBody TokenRequest request){
//        log.info("inside controler : ")
        try{
            return new ResponseEntity<>(authenticationService.checkToken(request.getToken()),HttpStatus.OK);
        }catch (Exception ex){
            ResponseMessage r = ResponseMessage.builder().message(ex.getMessage()).build();
            return new ResponseEntity<>(r,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
