package tacocloud.tacocloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tacocloud.tacocloud.dto.UserDto;
import tacocloud.tacocloud.mapper.UserMapper;
import tacocloud.tacocloud.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<UserDto> getUserByUsername(String username){
        return userRepository.getByUsername(username).map(UserMapper::toDto);
    }
}
