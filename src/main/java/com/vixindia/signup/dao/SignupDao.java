package com.vixindia.signup.dao;

import java.util.Map;

public interface SignupDao {

  Map<String, Object> getMobileVeificationOTPCount(Long mobileNumber);

  void updateMobileOtpCount(Long mobileNumber);

  void sendBorrowerMobileOtp(Long mobileNumber, String OTP);

  Map<String, Object> getOtpDetails(String mobileNumber);

  void updateTryCount(String mobileNumber);

  void setMobileVerified(String mobileNumber);

  void updateResetTryCount(String mobileNumber);

  void isMobileNumberAlreadyVerified(Long mobileNumber);

  Long insertProfile(String name, Long mobileNumber);
}
