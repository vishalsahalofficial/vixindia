package com.vixindia.signup.service;

import com.vixindia.custom_exception.GlobalException;
import com.vixindia.login.dao.UserDao;
import com.vixindia.login.dto.User;
import com.vixindia.signup.dao.SignupDao;
import com.vixindia.signup.dto.MobileValidationRequest;
import com.vixindia.signup.dto.MobileVerificationRequest;
import com.vixindia.signup.dto.SignUpRequest;
import com.vixindia.utils.HttpStatusConverter;
import com.vixindia.utils.Response;
import com.vixindia.utils.Validator;
import java.util.ArrayList;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SignupService {

  private static final Logger APP_LOG = LoggerFactory.getLogger(SignupService.class);
  @Autowired
  UserDao userDao;
  @Autowired
  Validator validator;
  @Autowired
  SignupDao signupDao;
  @Autowired
  HttpStatusConverter httpStatusConverter;

  public ResponseEntity<?> sendOTPForSignup(MobileVerificationRequest mobileVerificationRequest) {
    Map<String, Object> verifyDetails = null;
    try {

      if (mobileVerificationRequest.getMobileNumber() != null
          && mobileVerificationRequest.getMobileNumber() != 0) {
        Boolean isValid = validator.validateMobile(
            mobileVerificationRequest.getMobileNumber().toString());
        if (!isValid) {
          throw new GlobalException(HttpStatus.BAD_REQUEST.value(),
              "Please enter Valid Mobile Number", new ArrayList<>());
        }
        User userDetails = userDao.findByMobileNumber(
            mobileVerificationRequest.getMobileNumber().toString());
        if (userDetails != null) {
          throw new GlobalException(HttpStatus.BAD_REQUEST.value(),
              "User Already Exists with this mobile number", new ArrayList<>());
        }
        verifyDetails = signupDao.getMobileVeificationOTPCount(
            mobileVerificationRequest.getMobileNumber());

        if (verifyDetails != null &&
            Integer.parseInt(verifyDetails.get("otp_sent_try_count").toString()) >= 5 &&
            Integer.parseInt(verifyDetails.get("otp_life_time").toString()) < 5) {
          throw new GlobalException(HttpStatus.BAD_REQUEST.value(),
              "Maximum OTP limit exceeded, please try after 5 minutes.", new ArrayList<>());

        } else if (verifyDetails != null &&
            Integer.parseInt(verifyDetails.get("otp_sent_try_count").toString()) >= 3 &&
            Integer.parseInt(verifyDetails.get("otp_life_time").toString()) > 5) {
          signupDao.updateMobileOtpCount(mobileVerificationRequest.getMobileNumber());

        }
        signupDao.sendBorrowerMobileOtp(mobileVerificationRequest.getMobileNumber(), null);

        return sendResponse(HttpStatus.OK.value(), "SUCCESS", new ArrayList<>());

      } else {
        throw new GlobalException(HttpStatus.BAD_REQUEST.value(), "Bad Request", new ArrayList<>());

      }
    } catch (GlobalException e) {
      throw new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something Went Wrong",
          new ArrayList<>());

    }
  }

  public ResponseEntity<?> validateMobile(MobileValidationRequest mobileValidationRequest) {

    if (mobileValidationRequest.getOtp() != null && !mobileValidationRequest.getOtp().isEmpty()) {
      Boolean isValid = validator.validateMobile(
          mobileValidationRequest.getMobileNumber().toString());
      if (!isValid) {
        throw new GlobalException(HttpStatus.BAD_REQUEST.value(),
            "Please enter Valid Mobile Number", new ArrayList<>());
      }
      signupDao.isMobileNumberAlreadyVerified(mobileValidationRequest.getMobileNumber());
      ResponseEntity<?> errorList = validateMobileOtp(
          mobileValidationRequest.getMobileNumber().toString(), mobileValidationRequest.getOtp());

      if (errorList == null) {
        return sendResponse(HttpStatus.OK.value(), "Success", new ArrayList<>());
      }
    } else {
      throw new GlobalException(HttpStatus.BAD_REQUEST.value(), "BAD REQUEST", new ArrayList<>());
    }
    throw new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something Went Wrong",
        new ArrayList<>());
  }


  public ResponseEntity<?> validateMobileOtp(String mobileNumber, String otp) {
    Map<String, Object> recentOtpDetails = signupDao.getOtpDetails(mobileNumber);
    try {
      if (recentOtpDetails != null) {
        if (recentOtpDetails.get("mobile_otp_try_count") != null
            && Integer.parseInt(recentOtpDetails.get("mobile_otp_try_count").toString()) >= 3) {
          throw new GlobalException(HttpStatus.BAD_REQUEST.value(),
              "Maximum otp try limit reached. Please try after 5 minutes", new ArrayList<>());

        }
        signupDao.updateTryCount(mobileNumber);
        if (recentOtpDetails.get("mobile_otp") != null
            && recentOtpDetails.get("mobile_otp").toString().equals(otp)) {
          signupDao.setMobileVerified(mobileNumber);
          signupDao.updateResetTryCount(mobileNumber);
          return null;
        } else {
          throw new GlobalException(HttpStatus.BAD_REQUEST.value(),
              "Invalid OTP, Please check and try again.", new ArrayList<>());

        }
      } else {
        throw new GlobalException(HttpStatus.BAD_REQUEST.value(), "OTP Expired.",
            new ArrayList<>());
      }
    } catch (GlobalException e) {
      throw new GlobalException(e.getStatus(), e.getMessage(), e.getResultMap());
    }
  }

  public ResponseEntity<?> addCustomer(SignUpRequest signUpRequest) {

    if (validator.validateSignup(signUpRequest) == null) {
      Long userId = signupDao.insertProfile(signUpRequest.getName(),
          signUpRequest.getMobileNumber());
      return sendResponse(HttpStatus.OK.value(), "User Created", new ArrayList<>());
    }
    throw new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something Went Wrong",
        new ArrayList<>());
  }

  private ResponseEntity<?> sendResponse(Integer status, String message, Object result) {
    return new ResponseEntity<>(Response.builder()
        .status(status)
        .message(message)
        .result(result)
        .build(), httpStatusConverter.integerToHttpStatus(status));
  }
}
