package ir.smartpath.authenticationservice.utils.responses;

import ir.smartpath.authenticationservice.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    private User user;
    private String token;
}
