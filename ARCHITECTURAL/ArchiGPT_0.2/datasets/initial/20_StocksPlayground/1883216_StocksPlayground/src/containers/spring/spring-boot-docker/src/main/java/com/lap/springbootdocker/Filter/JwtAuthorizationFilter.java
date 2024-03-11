package com.lap.springbootdocker.Filter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter implements Filter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String JWT_COOKIE_NAME = "jwt";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);

        String jwt = extractJwtFromCookie(request.getCookies());

        if (jwt != null) {
            mutableRequest.putHeader(AUTHORIZATION_HEADER, "Bearer " + jwt);
        }

        chain.doFilter(mutableRequest, response);
    }

    private String extractJwtFromCookie(Cookie[] cookies) {
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (JWT_COOKIE_NAME.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
