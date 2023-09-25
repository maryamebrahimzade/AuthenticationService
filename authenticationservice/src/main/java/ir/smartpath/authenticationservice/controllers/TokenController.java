package ir.smartpath.authenticationservice.controllers;

import ir.smartpath.authenticationservice.services.TokenService;
import ir.smartpath.authenticationservice.utils.responses.TokenValidityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/token")
public class TokenController {
    @Autowired
    private TokenService service;

    @GetMapping("/is-valid")
    public TokenValidityResponse isTokenValid(@RequestHeader(name = "Authorization") String token) throws Exception {
        return service.isTokenValid(token);
    }
}
