package ir.smartpath.authenticationservice.controllers;

import ir.smartpath.authenticationservice.dtos.TimeDto;
import ir.smartpath.authenticationservice.dtos.UserDto;
import ir.smartpath.authenticationservice.models.User;
import ir.smartpath.authenticationservice.services.UserService;
import ir.smartpath.authenticationservice.utils.responses.TimeResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController extends BaseController<User, UserDto, UserService> {

    @GetMapping("/calc")
    public Object getFibonacciCalculation(@RequestParam Integer number, @RequestHeader(name = "Authorization") String token) throws Exception {
        return service.printFibonacciSeries(number, token);
    }

    @PostMapping("/intervals")
    public TimeResponse getNextIntervals(@Validated @RequestBody TimeDto timeDto) {
        return service.printNextIntervals(timeDto);
    }
}
