package tacocloud.tacocloud.mapper;


import lombok.experimental.UtilityClass;
import tacocloud.tacocloud.dto.UserDto;
import tacocloud.tacocloud.entity.UserEntity;

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
                .password("Hidden")
                .build();
    }
}
