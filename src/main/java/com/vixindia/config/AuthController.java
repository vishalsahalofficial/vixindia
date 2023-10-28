package com.vixindia.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
  @Autowired
  private JwtTokenProvider jwtTokenProvider;
  @Autowired
  private HttpServletRequest request;
  @GetMapping("/login")
  public String generateToken() {

    String token = jwtTokenProvider.generateToken("123");
    return token;
  }
  @GetMapping("/getUserId")
  public Long getUserId() {
    String token = request.getHeader("token");
    Long userIdFromToken = jwtTokenProvider.getUserIdFromToken(token);
    return userIdFromToken;
  }
}


