package ir.smartpath.authenticationservice.controllers;

import ir.smartpath.authenticationservice.dtos.LoginDto;
import ir.smartpath.authenticationservice.dtos.UserDto;
import ir.smartpath.authenticationservice.models.User;
import ir.smartpath.authenticationservice.services.UserService;
import ir.smartpath.authenticationservice.utils.responses.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController extends BaseController<User, UserDto, UserService> {
    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> register(@Validated @RequestBody UserDto dto) throws Exception {
        return ResponseEntity.ok(service.register(mapper.mapDto(dto)));
    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody LoginDto loginDto) throws Exception {
        return ResponseEntity.ok(service.login(loginDto));
    }
}
