package ir.smartpath.authenticationservice.utils.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeResponse {
    private StringBuffer nextIntervals;
}
