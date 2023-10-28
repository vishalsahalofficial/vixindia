package com.vixindia.signup.controller;

import com.vixindia.custom_exception.GlobalException;
import com.vixindia.signup.dto.MobileValidationRequest;
import com.vixindia.signup.dto.MobileVerificationRequest;
import com.vixindia.signup.dto.SignUpRequest;
import com.vixindia.signup.service.SignupService;
import com.vixindia.utils.HttpStatusConverter;
import com.vixindia.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignupController {

  @Autowired
  HttpStatusConverter httpStatusConverter;
  @Autowired
  SignupService signupService;

  @PostMapping("/send/signup/otp")
  public ResponseEntity<?> sendOTPForSignup(
      @RequestBody MobileVerificationRequest mobileVerificationRequest) {
    try {
      return signupService.sendOTPForSignup(mobileVerificationRequest);

    } catch (GlobalException e) {
      return sendResponse(e.getStatus(), e.getMessage(), e.getResultMap());
    }
  }

  @PostMapping("/send/signup/otp/verification")
  public ResponseEntity<?> verifyOTPForSignup(
      @RequestBody MobileValidationRequest mobileValidationRequest) {
    try {
      return signupService.validateMobile(mobileValidationRequest);
    } catch (GlobalException e) {
      return sendResponse(e.getStatus(), e.getMessage(), e.getResultMap());
    }
  }

  @PostMapping("/customer/signup")
  public ResponseEntity<?> addCustomer(@RequestBody SignUpRequest signUpRequest) {

    try {
      return signupService.addCustomer(signUpRequest);
    } catch (GlobalException e) {
      return sendResponse(e.getStatus(), e.getMessage(), e.getResultMap());
    }
  }

  private ResponseEntity<?> sendResponse(Integer status, String message, Object result) {
    return new ResponseEntity<>(Response.builder()
        .status(status)
        .message(message)
        .result(result)
        .build(), httpStatusConverter.integerToHttpStatus(status));
  }
}
