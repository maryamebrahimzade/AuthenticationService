package ir.smartpath.authenticationservice.services;

import ir.smartpath.authenticationservice.config.cache.RedisService;
import ir.smartpath.authenticationservice.config.security.jwt.JwtService;
import ir.smartpath.authenticationservice.utils.responses.TokenValidityResponse;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtService jwtService;
    private final RedisService redisService;
    @Value("${redis.prefix}")
    private String redisPrefix;

    public TokenValidityResponse isTokenValid(String token) throws Exception {

        if (token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            try {
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                if (principal == null) {
                    return TokenValidityResponse.builder().isValid(false).build();
                }

                Boolean tokenExpired = jwtService.isTokenExpired(jwtToken);
                if (tokenExpired) {
                    return TokenValidityResponse.builder().isValid(false).build();
                }

                String idFromToken = jwtService.extractId(jwtToken);
                Object idInRedis = redisService.get(redisPrefix + idFromToken);

                if (idInRedis == null) {
                    return TokenValidityResponse.builder().isValid(true).build();
                }

            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        }
        return TokenValidityResponse.builder().isValid(false).build();
    }
}
