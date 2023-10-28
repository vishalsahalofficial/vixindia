package com.vixindia.login.service;

import com.vixindia.login.dao.UserDao;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

  @Autowired
  private UserDao userDao;

  public boolean generateAndSendOtp(String mobileNumber) {
    // generate and send OTP to the user's mobile number
    // return true if successful, false otherwise

    String otp = generateOtp();
    if (sendSms(mobileNumber, otp)) {
      userDao.updateOtp(mobileNumber, otp);
      return true;
    } else {
      return false;
    }
  }

  public String generateOtp() {
    // generate random 6-digit OTP
    return String.format("%06d", new Random().nextInt(999999));
  }

  private boolean sendSms(String mobileNumber, String otp) {
    // send OTP via SMS to the mobile number
    // return true if successful, false otherwise
    return true;
  }
}
