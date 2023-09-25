package ir.smartpath.authenticationservice.mappers;

import ir.smartpath.authenticationservice.dtos.UserDto;
import ir.smartpath.authenticationservice.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserDto, User> {
}
