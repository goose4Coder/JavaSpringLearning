package tacocloud.tacocloud.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacocloud.tacocloud.dto.UserDto;
import tacocloud.tacocloud.entity.UserEntity;
import tacocloud.tacocloud.repository.UserRepository;
import tacocloud.tacocloud.utils.JwtUtil;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserRepository repository;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private JwtUtil util;
    @InjectMocks
    private AuthService service;

    @Test
    public void testRegisterUser_registersCorrectly(){
        Mockito.lenient().when(encoder.encode(any(CharSequence.class))).thenReturn("12345");
        UserDto dto = UserDto.builder().id(1L).username("UserOne").password("abc").build();
        UserEntity entity=UserEntity.builder().id(3L).username("UserOne").password("12345").build();
        when(repository.save(any(UserEntity.class))).thenReturn(entity);
        when(repository.usernameExists(anyString())).thenReturn(false);
        UserDto toCheck = service.registerUser(dto);
        Assertions.assertEquals("UserOne",toCheck.getUsername());
        Assertions.assertEquals(3L,toCheck.getId());
        Assertions.assertEquals("Hidden for security reasons",toCheck.getPassword());
    }

    @Test
    public void testRegisterUser_failsWhenUsernameAlreadyExists(){
        Mockito.lenient().when(encoder.encode(any(CharSequence.class))).thenReturn("12345");
        UserDto dto = UserDto.builder().id(1L).username("UserOne").password("abc").build();
        UserEntity entity=UserEntity.builder().id(3L).username("UserOne").password("12345").build();
        when(repository.save(any(UserEntity.class))).thenReturn(entity);
        when(repository.usernameExists("UserOne")).thenReturn(true);
        try {
            UserDto toCheck = service.registerUser(dto);
        } catch (Exception e) {
            Assertions.assertEquals(new BadCredentialsException("User with the given username already exists").getCause(),e.getCause());
            Assertions.assertEquals("User with the given username already exists",e.getMessage());
        }
    }

    @Test
    public void testLoginUser_loginsCorrectly(){
        Mockito.lenient().when(encoder.encode(any(CharSequence.class))).thenReturn("abc");
        UserDto dto = UserDto.builder().id(1L).username("UserOne").password("12345").build();
        UserEntity entity=UserEntity.builder().id(3L).username("UserOne").password("abc").build();

        when(repository.getByUsername("UserOne")).thenReturn(Optional.of(entity));
        when(encoder.matches("12345","abc")).thenReturn(true);
        when(util.generateToken("UserOne")).thenReturn("bc0");

        String toCheck = service.loginUser(dto);
        Assertions.assertEquals("bc0",toCheck);
    }

    @Test
    public void testLoginUser_failsCorrectlyWhenUsernameNotFound(){
        Mockito.lenient().when(encoder.encode(any(CharSequence.class))).thenReturn("abc");
        UserDto dto = UserDto.builder().id(1L).username("UserOne").password("12345").build();
        UserEntity entity=UserEntity.builder().id(3L).username("UserTwo").password("abc").build();

        Mockito.lenient().when(repository.getByUsername(anyString())).thenReturn(Optional.empty());
        Mockito.lenient().when(repository.getByUsername("UserTwo")).thenReturn(Optional.of(entity));
        Mockito.lenient().when(encoder.matches("12345","abc")).thenReturn(true);
        Mockito.lenient().when(util.generateToken("UserOne")).thenReturn("bc0");

        try {
            String toCheck = service.loginUser(dto);
            Assertions.fail("Returned token for inexistent user");
        } catch (Exception e) {
            Assertions.assertEquals(new UsernameNotFoundException("User not found").getCause(),e.getCause());
            Assertions.assertEquals("User not found",e.getMessage());
        }
    }

    @Test
    public void testLoginUser_failsWithIncorrectPassword(){
        Mockito.lenient().when(encoder.encode(any(CharSequence.class))).thenReturn("abc");
        UserDto dto = UserDto.builder().id(1L).username("UserOne").password("12345").build();
        UserEntity entity=UserEntity.builder().id(3L).username("UserOne").password("abc").build();
        Mockito.lenient().when(repository.getByUsername("UserOne")).thenReturn(Optional.of(entity));
        Mockito.lenient().when(encoder.matches(anyString(),anyString())).thenReturn(false);
        Mockito.lenient().when(encoder.matches("ThePassword","abc")).thenReturn(true);

        Mockito.lenient().when(util.generateToken("UserOne")).thenReturn("bc0");

        try {
            String toCheck = service.loginUser(dto);
            Assertions.fail("Returned token for inexistent user");
        } catch (Exception e) {
            Assertions.assertEquals(new BadCredentialsException("Invalid password").getCause(),e.getCause());
            Assertions.assertEquals("Invalid password",e.getMessage());
        }
    }

//    @Test
//    public void testGetDetailsByUsername_failsWithIncorrectPassword(){
//        UserEntity entity=UserEntity.builder().id(3L).username("UserOne").password("abc").build();
//        when(repository.getByUsername("UserOne")).thenReturn(Optional.of(entity));
//        UserDetails details=service.getDetailsByUsername("UserOne");
//        details.
//    }
}
