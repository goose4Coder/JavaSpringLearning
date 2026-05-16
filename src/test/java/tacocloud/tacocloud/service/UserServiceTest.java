package tacocloud.tacocloud.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacocloud.tacocloud.dto.UserDto;
import tacocloud.tacocloud.entity.UserEntity;
import tacocloud.tacocloud.repository.UserRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository repository;
    @Mock
    private PasswordEncoder encoder;
    @InjectMocks
    private UserService service;

    @Test
    public void testGetByUsername_getsCorrectly(){
        Mockito.lenient().when(encoder.encode(any(CharSequence.class))).thenReturn("12345");
        UserEntity entity=UserEntity.builder().id(3L).username("UserOne").password("123ABC").build();
        Mockito.lenient().when(repository.getByUsername(anyString())).thenReturn(Optional.empty());
        Mockito.lenient().when(repository.getByUsername("UserOne")).thenReturn(Optional.of(entity));
        UserEntity entity2=UserEntity.builder().id(4L).username("UserTwo").password("123ABC").build();
        Mockito.lenient().when(repository.getByUsername("UserTwo")).thenReturn(Optional.of(entity2));

        Optional<UserDto> toCheck = service.getUserByUsername("UserOne");
        Assertions.assertTrue(toCheck.isPresent());
        Assertions.assertEquals("UserOne",toCheck.get().getUsername());
        Assertions.assertEquals(3L,toCheck.get().getId());
        Assertions.assertEquals("Hidden for security reasons",toCheck.get().getPassword());
    }

    @Test
    public void testGetByUsername_nullOnInexistentUsername(){
        Mockito.lenient().when(encoder.encode(any(CharSequence.class))).thenReturn("12345");
        UserEntity entity=UserEntity.builder().id(3L).username("UserOne").password("123ABC").build();
        Mockito.lenient().when(repository.getByUsername(anyString())).thenReturn(Optional.empty());
        Mockito.lenient().when(repository.getByUsername("UserOne")).thenReturn(Optional.of(entity));
        UserEntity entity2=UserEntity.builder().id(4L).username("UserTwo").password("123ABC").build();
        Mockito.lenient().when(repository.getByUsername("UserTwo")).thenReturn(Optional.of(entity2));

        Optional<UserDto> toCheck = service.getUserByUsername("Hnfds");
        Assertions.assertTrue(toCheck.isEmpty());
    }
    @Test
    public void testById_getsOfCorrectId(){
        Mockito.lenient().when(encoder.encode(any(CharSequence.class))).thenReturn("12345");
        UserEntity entity=UserEntity.builder().id(3L).username("UserOne").password("123ABC").build();
        Mockito.lenient().when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.lenient().when(repository.findById(3L)).thenReturn(Optional.of(entity));
        UserEntity entity2=UserEntity.builder().id(4L).username("UserTwo").password("123ABC").build();
        Mockito.lenient().when(repository.findById(4L)).thenReturn(Optional.of(entity2));

        Optional<UserDto> toCheck = service.byId(3L);
        Assertions.assertTrue(toCheck.isPresent());
        Assertions.assertEquals("UserOne",toCheck.get().getUsername());
        Assertions.assertEquals(3L,toCheck.get().getId());
        Assertions.assertEquals("Hidden for security reasons",toCheck.get().getPassword());
    }

    @Test
    public void testById_nullOnInexistentId(){
        Mockito.lenient().when(encoder.encode(any(CharSequence.class))).thenReturn("12345");
        UserEntity entity=UserEntity.builder().id(3L).username("UserOne").password("123ABC").build();
        Mockito.lenient().when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.lenient().when(repository.findById(3L)).thenReturn(Optional.of(entity));
        UserEntity entity2=UserEntity.builder().id(4L).username("UserTwo").password("123ABC").build();
        Mockito.lenient().when(repository.findById(4L)).thenReturn(Optional.of(entity2));

        Optional<UserDto> toCheck = service.byId(1L);
        Assertions.assertTrue(toCheck.isEmpty());
    }
    @Test
    public void createUser_CreatesCorrectly(){
        Mockito.lenient().when(encoder.encode(any(CharSequence.class))).thenReturn("12345");
        UserDto dto = UserDto.builder().id(1L).username("UserOne").password("abc").build();
        UserEntity entity=UserEntity.builder().id(3L).username("UserOne").password("12345").build();

        Mockito.lenient().when(repository.save(any(UserEntity.class))).thenReturn(entity);

        UserDto toCheck = service.createUser(dto);
        Assertions.assertEquals("UserOne",toCheck.getUsername());
        Assertions.assertEquals(3L,toCheck.getId());
        Assertions.assertEquals("Hidden for security reasons",toCheck.getPassword());

    }

    @Test
    public void updateUser_UpdatesCorrectly(){
        Mockito.lenient().when(encoder.encode(any(CharSequence.class))).thenReturn("12345");
        UserDto dto = UserDto.builder().id(1L).username("UserOne").password("abc").build();
        UserEntity entity=UserEntity.builder().id(3L).username("UserOne").password("12345").build();

        Mockito.lenient().when(repository.save(any(UserEntity.class))).thenReturn(entity);

        UserDto toCheck = service.updateUser(3L,dto);
        Assertions.assertEquals("UserOne",toCheck.getUsername());
        Assertions.assertEquals(3L,toCheck.getId());
        Assertions.assertEquals("Hidden for security reasons",toCheck.getPassword());

    }

}
