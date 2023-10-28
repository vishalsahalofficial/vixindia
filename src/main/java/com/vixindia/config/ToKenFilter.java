package com.vixindia.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vixindia.custom_exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class ToKenFilter implements Filter {

    @Autowired
    private ObjectMapper om;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static final Logger APP_LOG = LoggerFactory.getLogger(ToKenFilter.class);
    private static String jwtSecret = "1C850C8035CEF4A0492DA4947E872DB24F1881F32B4CC93B26CD9D82CB64595F567890FFGCHVJKLGHJBKNL";


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException, InvalidTokenException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String token = request.getHeader("token");

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Allow-Headers",
                    "Content-Type, Accept, X-Requested-With, remember-me, token,Authorization");
            chain.doFilter(req, res);
        } else {

            String request_type = request.getMethod();
            String request_endpoint = request.getRequestURI();


            if (request_endpoint.contains("generate")) {
                chain.doFilter(request, response);
                return;
            }

            if (token == null || token.length() < 64) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized User");
                return;
            }


            final Claims claims = extractClaims(token);
            request.setAttribute("claims", claims);

            chain.doFilter(req, res);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    private static Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token).getBody();
    }
}
