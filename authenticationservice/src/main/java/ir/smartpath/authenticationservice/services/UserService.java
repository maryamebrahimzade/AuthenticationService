package ir.smartpath.authenticationservice.services;

import ir.smartpath.authenticationservice.config.cache.RedisService;
import ir.smartpath.authenticationservice.config.security.jwt.JwtService;
import ir.smartpath.authenticationservice.dtos.LoginDto;
import ir.smartpath.authenticationservice.dtos.TimeDto;
import ir.smartpath.authenticationservice.enums.Role;
import ir.smartpath.authenticationservice.exceptions.ServiceException;
import ir.smartpath.authenticationservice.models.User;
import ir.smartpath.authenticationservice.repositories.UserRepository;
import ir.smartpath.authenticationservice.utils.responses.AuthenticationResponse;
import ir.smartpath.authenticationservice.utils.responses.TimeResponse;
import ir.smartpath.authenticationservice.utils.responses.TokenValidityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService extends BaseService<UserRepository, User> {
    @Autowired
    private TokenService serviceToken;
    @Value("${fibonacci.id.validity}")
    private long FIBONACCI_ID_VALIDITY;
    @Value("${redis.prefix}")
    private String redisPrefix;
    @Value("${intervals.next}")
    private Integer intervalsNext;
    @Value("${intervals.length}")
    private Integer intervalsLength;
    @Value("${intervals.second}")
    private String intervalsSecond;
    @Value("${intervals.milliSeconds}")
    private String intervalsMilliSeconds;

    private final RedisService redisService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public ResponseEntity<User> register(User user) throws Exception {
        if (repository.existsByUsername(user.getUsername())) {
            throw new ServiceException("username-is-already-taken");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        repository.save(user);
        return ResponseEntity.ok(user);
    }

    public AuthenticationResponse login(LoginDto loginDto) throws Exception {
        authenticate(loginDto.getUsername(), loginDto.getPassword());
        var user = repository.findByUsername(loginDto.getUsername()).orElseThrow();
        final String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(token).user(user).build();
    }

    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new ServiceException(e, "user-disabled");
        } catch (BadCredentialsException e) {
            throw new ServiceException(e, "invalid-credentials");
        }
    }

    public Object printFibonacciSeries(Integer number, String token) throws Exception {
        TokenValidityResponse validToken = serviceToken.isTokenValid(token);

        if (validToken.getIsValid()) {
            Object fibonacciInRedis = redisService.get(redisPrefix + number);
            if (fibonacciInRedis == null) {
                return getFibonacciCalculation(number);
            }
            return fibonacciInRedis;

        } else {
            throw new ServiceException("only-accessible-to-logged-in-users");
        }
    }

    private StringBuffer getFibonacciCalculation(Integer number) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < number; i++) {
            result.append(fibRecursion(i)).append(" ");
        }
        saveResultOnRedis(number, result);
        return result;
    }

    private void saveResultOnRedis(Integer number, StringBuffer result) {
        redisService.set(
                number.toString(),
                result,
                FIBONACCI_ID_VALIDITY,
                TimeUnit.SECONDS
        );
    }

    public static Integer fibRecursion(Integer number) {
        if (number == 0) {
            return 0;
        }
        if (number == 1 || number == 2)
            return 1;
        return fibRecursion(number - 1) + fibRecursion(number - 2);
    }

    public TimeResponse printNextIntervals(TimeDto timeDto) {
        StringBuffer result = new StringBuffer();
        getNextIntervals(timeDto, result);
        result.replace(49, 52, "");
        return TimeResponse.builder().nextIntervals(result).build();
    }

    private void getNextIntervals(TimeDto timeDto, StringBuffer result) {
        for (int i = 0; i < intervalsNext; i++) {
            int hour = Integer.parseInt(timeDto.getTime().substring(0, 2));
            int minute = Integer.parseInt(timeDto.getTime().substring(3, 5));
            minute += intervalsLength;
            if (minute >= 60) {
                minute = 0;
                hour += 1;
                getTime(timeDto, hour, minute);
            } else {
                getTime(timeDto, hour, minute);
            }
            result.append(new Date(System.currentTimeMillis())).append(" ").append(timeDto.getTime());
        }
    }

    private void getTime(TimeDto timeDto, int hour, int minute) {
        String time = minute + ":" + intervalsSecond + "." + intervalsMilliSeconds + " - ";
        if (getCountHourDigits(hour) == 1 && minute != 0) {
            timeDto.setTime("0" + hour + ":" + time);
        } else if (getCountHourDigits(hour) == 1 && minute == 0) {
            timeDto.setTime("0" + hour + ":" + "0" + time);
        } else if (getCountHourDigits(hour) != 1 && minute == 0) {
            timeDto.setTime(hour + ":" + "0" + time);
        } else if (getCountHourDigits(hour) != 1) {
            timeDto.setTime(hour + ":" + time);
        }
    }

    private static int getCountHourDigits(int hour) {
        int count = 0;
        while (hour != 0) {
            hour /= 10;
            count++;
        }
        return count;
    }
}

