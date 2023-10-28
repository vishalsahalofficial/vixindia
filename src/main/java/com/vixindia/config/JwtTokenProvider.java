package com.vixindia.config;

import com.vixindia.custom_exception.InvalidTokenException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;


@Component
public class JwtTokenProvider {

    //    @Value("${app.jwtSecret}")
    private String jwtSecret = "1C850C8035CEF4A0492DA4947E872DB24F1881F32B4CC93B26CD9D82CB64595F567890FFGCHVJKLGHJBKNL";

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public JwtTokenProvider() {
        jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
    }

    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 86400000); // 1 day
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String token) throws InvalidTokenException {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            throw new InvalidTokenException("Invalid JWT signature", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (MalformedJwtException ex) {
            throw new InvalidTokenException("Invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ExpiredJwtException ex) {
            throw new InvalidTokenException("Expired JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (UnsupportedJwtException ex) {
            throw new InvalidTokenException("Unsupported JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException ex) {
            throw new InvalidTokenException("JWT claims string is empty", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}




