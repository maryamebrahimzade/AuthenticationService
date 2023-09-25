package ir.smartpath.authenticationservice.exceptions;

import lombok.Data;

@Data
public class ExceptionResponse {
    private String message;
    private Boolean error;
}
