// 인증되지 않은 사용자가 접근할 경우 에러 발생 후 로그인 페이지로 리다이렉트
package com.shoping.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        if("XMLHttpRequest".equals(request.getHeader("x-requested-with"))){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        } else {
            response.sendRedirect("/members/login");
        }
    }
}
