package ir.smartpath.authenticationservice.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder

public class TimeDto extends BaseDto {
    @NotNull(message = "Entering this field is mandatory")
    @Pattern(regexp = "^\\d{2}+:\\d{2}+:\\d{2}+\\.\\d{3}$" ,message = "Invalid date format. The expected format is HH:mm:ss.SSS")
    private String time;
    @JsonCreator
    public TimeDto(@JsonProperty("time") String time) {
        this.time = time;
    }
}
