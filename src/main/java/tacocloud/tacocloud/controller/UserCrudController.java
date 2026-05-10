package tacocloud.tacocloud.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacocloud.tacocloud.dto.UserDto;
import tacocloud.tacocloud.service.UserService;

import java.util.List;

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
}
