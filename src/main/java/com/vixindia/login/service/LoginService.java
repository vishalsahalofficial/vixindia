package com.vixindia.login.service;

import com.vixindia.config.AuthController;
import com.vixindia.custom_exception.GlobalException;
import com.vixindia.login.dao.UserDao;
import com.vixindia.login.dto.User;
import com.vixindia.utils.HttpStatusConverter;
import com.vixindia.utils.Response;
import com.vixindia.utils.Validator;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  private static final Logger APP_LOG = LoggerFactory.getLogger(LoginService.class);
  @Autowired
  private OtpService otpService;
  @Autowired
  private UserDao userDao;
  @Autowired
  AuthController authController;

  @Autowired
  Validator validator;

  @Autowired
  HttpStatusConverter httpStatusConverter;

  public ResponseEntity<?> loginRequest(String mobileNumber) {

    Map<String, Object> responseMap = new LinkedHashMap<>();

    if (!validator.validateMobile(mobileNumber)) {
      throw new GlobalException(HttpStatus.BAD_REQUEST.value(),
          "Please Enter Valid Correct Mobile Number", new ArrayList<>());
    }
    User userDetails = userDao.findByMobileNumber(mobileNumber);

    if (userDetails != null) {
      Boolean isNeedToSendOTP = userDao.isNeedToSendOTP(mobileNumber);

      if (!isNeedToSendOTP) {
        throw new GlobalException(HttpStatus.BAD_REQUEST.value(),
            "Maximum OTP try limit reached. Please try after 5 minutes.", new ArrayList<>());
      }
      otpService.generateAndSendOtp(mobileNumber);
      String token = authController.generateToken();
      userDao.updateToken(token, mobileNumber);
      responseMap.put("status", "OTP Sent");
      responseMap.put("token", token);
      return sendResponse(HttpStatus.OK.value(), "Success", responseMap);
    } else {
      return sendResponse(HttpStatus.BAD_REQUEST.value(), "No Data Found", new ArrayList<>());
    }
  }


  public ResponseEntity<?> verifyOTP(String mobileNumber, String otp) {
    try {
      Boolean isValid = validator.validateMobile(mobileNumber);
      if (!isValid) {
        throw new GlobalException(HttpStatus.BAD_REQUEST.value(),
            "Please Enter Valid Correct Mobile Number", new ArrayList<>());
      }

      User user = userDao.findByMobileNumber(mobileNumber);
      if (user.getOtpTryCount() <= 5) {
        if (otp.equalsIgnoreCase(user.getOtp())) {
          userDao.updateOtpTryCountAndVerification(user.getMobileNumber(), 0, 1);
          return sendResponse(HttpStatus.OK.value(), "Login successful", new ArrayList<>());
        } else {
          userDao.updateOtpTryCountAndVerification(user.getMobileNumber(),
              user.getOtpTryCount() + 1, null);
          throw new GlobalException(HttpStatus.BAD_REQUEST.value(),
              "Invalid OTP, Please check and try again.", new ArrayList<>());

        }
      } else {
        throw new GlobalException(HttpStatus.BAD_REQUEST.value(),
            "You have reached maximum try count.", new ArrayList<>());
      }

    } catch (GlobalException e) {
      throw new GlobalException(e.getStatus(), e.getMessage(), e.getResultMap());
    } catch (Exception e) {
      throw new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something Went Wrong",
          new ArrayList<>());
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
