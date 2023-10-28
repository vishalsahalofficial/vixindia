package com.vixindia.login.dao;

import com.vixindia.login.dto.User;

public interface UserDao {
    User findByMobileNumber(String mobileNumber);

    void updateOtp(String mobileNumber, String otp);

    Boolean isNeedToSendOTP(String mobileNumber);

    void updateOtpTryCountAndVerification(String mobileNumber, Integer tryCount, Integer isMobileVerified);

    void updateToken(String token, String mobileNumber);

}