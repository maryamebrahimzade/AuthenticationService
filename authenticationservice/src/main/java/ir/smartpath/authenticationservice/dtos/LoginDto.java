package ir.smartpath.authenticationservice.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class LoginDto extends BaseDto {
    @NotNull(message = "Entering this field is mandatory")
    @Pattern(regexp = "^([a-zA-Z]|\\s){3,10}$", message = "your username must be between 3 and 10 characters long.")
    private String username;
    @NotNull(message = "Entering this field is mandatory")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,20}$", message = "Use 8 or more characters with a mix of letters,numbers & symbols for your password")
    private String password;

}
