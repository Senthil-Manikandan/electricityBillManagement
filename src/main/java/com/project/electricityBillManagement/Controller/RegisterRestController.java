package com.project.electricityBillManagement.Controller;

import com.project.electricityBillManagement.model.Register;
import com.project.electricityBillManagement.payload.request.ForgotPasswordRequest;
import com.project.electricityBillManagement.payload.request.LoginRequest;
import com.project.electricityBillManagement.payload.wrapper.RegisterPasswordWrapper;
import com.project.electricityBillManagement.payload.wrapper.RegisterWrapper;
import com.project.electricityBillManagement.service.impl.RegisterServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/home")
@Slf4j
public class RegisterRestController {
    private final RegisterServiceImpl registerService;


    @Autowired
    public RegisterRestController(RegisterServiceImpl registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> createAccount(@RequestBody Register register){
        try{
            Register newRegister = registerService.signup(register);
            return new ResponseEntity<>(newRegister.toString(), HttpStatus.CREATED);
        }catch(Exception ex){
            return  new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> logInAccount(@RequestBody LoginRequest loginRequest){
        try{
            String token = registerService.logIn(loginRequest);
            log.info("token login : {}",token);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }catch(Exception ex){
            return  new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<List<RegisterWrapper>> getAllUser() {
        try {
            List result = registerService.getAllUser();
            if (result.isEmpty())
                return new ResponseEntity<>(result,HttpStatus.UNAUTHORIZED);
            else
                return new ResponseEntity<>(result,HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
//        return ResponseEntity.ok("Hello from secured endpoint");
    }

    @GetMapping("/getallstring")
    public ResponseEntity<String> getString() {
        return ResponseEntity.ok("Hello from secured endpoint");
    }

    @PostMapping("/changepassword")
    public ResponseEntity<String> changePassword(@RequestBody LoginRequest loginRequest){
        try{
             return new ResponseEntity<>(registerService.changePassword(loginRequest),HttpStatus.OK);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return  new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
