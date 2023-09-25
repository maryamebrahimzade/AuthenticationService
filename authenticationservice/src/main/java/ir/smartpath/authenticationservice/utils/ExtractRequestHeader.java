package ir.smartpath.authenticationservice.utils;

import jakarta.servlet.http.HttpServletRequest;

public class ExtractRequestHeader {
    public static String getHeaderAuthorization(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public static String getHeaderPureToken(HttpServletRequest request) {
        return getHeaderAuthorization(request).substring(7);
    }

    public static Boolean tokenIsBearer(String token) {
        return token.startsWith("Bearer ");
    }
}
