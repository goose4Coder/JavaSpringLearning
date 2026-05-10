package tacocloud.tacocloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tacocloud.tacocloud.dto.UserDto;
import tacocloud.tacocloud.entity.UserEntity;
import tacocloud.tacocloud.mapper.UserMapper;
import tacocloud.tacocloud.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<UserDto> getUserByUsername(String username){
        return repository.getByUsername(username).map(UserMapper::toDto);
    }

    public List<UserDto> listAllUsers(){
        return repository.findAll().stream().map(UserMapper::toDto).toList();
    }

    public Optional<UserDto> byId(Long id){
        return repository.findById(id).map(UserMapper::toDto);
    }

    public UserDto createUser(UserDto user){
        UserEntity toSave=UserMapper.toEntity(user);
        toSave.setPassword(passwordEncoder.encode(user.getPassword()));
        return UserMapper.toDto(repository.save(toSave));
    }

    public UserDto updateUser(Long id, UserDto user){
        UserEntity toSave=UserMapper.toEntity(user);
        toSave.setId(id);
        toSave.setPassword(passwordEncoder.encode(user.getPassword()));
        return UserMapper.toDto(repository.save(toSave));
    }

    public void deleteUser(Long id){
        repository.deleteById(id);
    }


}
