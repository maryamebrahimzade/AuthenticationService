package ir.smartpath.authenticationservice.dtos;

import lombok.Data;

import java.util.Date;

@Data
public abstract class BaseDto {
    private Long Id;
    private Integer version;
    private Date insertTimeStamp;
    private Date lastUpdateTimestamp;
}
