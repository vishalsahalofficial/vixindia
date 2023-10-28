package com.vixindia.utils;

import com.vixindia.custom_exception.GlobalException;
import com.vixindia.signup.dto.SignUpRequest;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class Validator {

  public Boolean validateMobile(String mobileNumber) {
    Pattern mobilePattern = Pattern.compile("^[6-9][0-9]{9}$");
    Matcher matcher = mobilePattern.matcher(mobileNumber);
    if (matcher.matches()) {
      return Boolean.TRUE;
    } else {
      return Boolean.FALSE;
    }
  }

  public Boolean validateEmail(String email) {
    Pattern mobilePattern = Pattern.compile(
        "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    Matcher matcher = mobilePattern.matcher(email);
    if (matcher.matches()) {
      return Boolean.TRUE;
    } else {
      return Boolean.FALSE;
    }
  }

  public Integer validateSignup(SignUpRequest request) {

    if (request.getName().trim().isEmpty()) {
      throw new GlobalException(HttpStatus.BAD_REQUEST.value(), "Name can't be empty",
          new ArrayList<>());
    }
    if (!validateMobile(request.getMobileNumber().toString())) {
      throw new GlobalException(HttpStatus.BAD_REQUEST.value(),
          "Please Enter Valid Correct Mobile Number", new ArrayList<>());
    }
    return null;
  }
}
