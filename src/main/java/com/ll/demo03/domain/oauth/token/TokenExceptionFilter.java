package com.ll.demo03.domain.oauth.token;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.ll.demo03.global.error.ErrorCode;
import com.ll.demo03.global.exception.CustomException;
import org.springframework.web.filter.OncePerRequestFilter;

public class TokenExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) {

        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
    }
}
