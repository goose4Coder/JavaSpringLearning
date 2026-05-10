package tacocloud.tacocloud.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacocloud.tacocloud.dto.*;
import tacocloud.tacocloud.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user/")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserCrudController {
    @Autowired
    private UserService service;
    @GetMapping
    public List<UserDto> getAllUsers(){
        return service.listAllUsers();
    }
    @GetMapping("/{id}/")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return service.byId(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserReport> createUser(@RequestBody UserDto dto) {
        UserDto result = service.createUser(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new UserReport(result));
    }

    @PatchMapping("/{id}/")
    public ResponseEntity<UserReport> patchUser(@PathVariable Long id, @RequestBody UserDto user){
        if (service.byId(id).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UserReport("User with the requested id does not exist"));
        }
        Optional<UserDto> ofUsername = service.getUserByUsername(user.getUsername());
        if (ofUsername.isPresent()){
            if (!ofUsername.get().getId().equals(user.getId())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserReport("User with this username already exists"));
            }
        }
        UserDto updated = service.updateUser(id,user);
        return ResponseEntity.status(HttpStatus.OK).body(new UserReport(updated));
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<UserReport> deleteUser(@PathVariable Long id){
        Optional<UserDto> deleted = service.byId(id);
        if (deleted.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UserReport("User with the requested id does not exist"));
        }
        service.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(new UserReport(deleted.get()));
    }

}
