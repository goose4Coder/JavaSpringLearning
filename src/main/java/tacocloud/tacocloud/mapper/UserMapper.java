package tacocloud.tacocloud.mapper;


import lombok.experimental.UtilityClass;
import org.springframework.security.core.userdetails.UserDetails;
import tacocloud.tacocloud.dto.UserDto;
import tacocloud.tacocloud.entity.UserEntity;

import java.util.Collections;

@UtilityClass
public class UserMapper {
    static public UserEntity toEntity(UserDto user){
        return UserEntity.builder()
                .username(user.getUsername())
                .build();
    }

    static public UserEntity parseRegistration(UserDto user){
        return UserEntity.builder()
                .username(user.getUsername())
                .build();
    }
    static public UserDto toDto(UserEntity user){
        return UserDto.builder()
                .username(user.getUsername())
                .id(user.getId())
                .password("Hidden for security reasons")
                .build();
    }

    static public UserDetails asDetails(UserEntity user){
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.emptyList()
        );
    }
}
