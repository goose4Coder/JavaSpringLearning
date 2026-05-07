package tacocloud.tacocloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tacocloud.tacocloud.dto.UserDto;
import tacocloud.tacocloud.entity.UserEntity;
import tacocloud.tacocloud.mapper.UserMapper;
import tacocloud.tacocloud.repository.UserRepository;
import tacocloud.tacocloud.utils.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public UserDto registerUser(UserDto user) {
        if (userRepository.usernameExists(user.getUsername())){
            throw new BadCredentialsException("User with the given username already exists");
        }
        UserEntity userToSave = UserMapper.parseRegistration(user);
        userToSave.setPassword(passwordEncoder.encode(user.getPassword()));
        return UserMapper.toDto(userRepository.save(userToSave));
    }

    public String loginUser(UserDto login) {
        UserEntity user = userRepository.getByUsername(login.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            return jwtUtil.generateToken(login.getUsername());
        } else {
            throw new BadCredentialsException("Invalid password");
        }
    }

    public void changePassword(String token, String newPassword) {
        String username=jwtUtil.extractUsername(token);
        UserEntity userToSave = userRepository.getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userToSave.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(userToSave);
    }
}