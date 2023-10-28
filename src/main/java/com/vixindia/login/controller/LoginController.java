package com.vixindia.login.controller;

import com.vixindia.config.AuthController;
import com.vixindia.custom_exception.GlobalException;
import com.vixindia.login.dao.UserDao;
import com.vixindia.login.service.LoginService;
import com.vixindia.login.service.OtpService;
import com.vixindia.utils.HttpStatusConverter;
import com.vixindia.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

@RestController
public class LoginController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private OtpService otpService;

    @Autowired
    AuthController authController;

    @Autowired
    HttpStatusConverter httpStatusConverter;

    @Autowired
    LoginService loginService;

    @CrossOrigin(origins = "http://localhost:63342")
    @PostMapping("/login/generate/otp")
    public ResponseEntity<?> login(@RequestParam("mobile_number") String mobileNumber) {

        Map<String, Object> responseMap = new LinkedHashMap<>();

        try {
            return loginService.loginRequest(mobileNumber);

        } catch (GlobalException e) {
            return sendResponse(e.getStatus(), e.getMessage(), e.getResultMap());

        } catch (Exception e) {
            throw new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something Went Wrong", new ArrayList<>());
        }
    }


    @CrossOrigin(origins = "http://localhost:63342")
    @PostMapping("vixindia/verify/otp")
    public ResponseEntity<?> verify(@RequestParam("mobile_number") String mobileNumber, @RequestParam("otp") String
            otp) {

        try {
            return loginService.verifyOTP(mobileNumber, otp);
        } catch (GlobalException e) {
            return sendResponse(e.getStatus(), e.getMessage(), e.getResultMap());
        } catch (Exception e) {
            throw new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something Went Wrong", new ArrayList<>());
        }

    }

    private ResponseEntity<?> sendResponse(Integer status, String message, Object result) {
        Response response = new Response();
        response.setStatus(status);
        response.setMessage(message);
        response.setResult(result);
        return new ResponseEntity<>(response, httpStatusConverter.integerToHttpStatus(status));
    }
}

