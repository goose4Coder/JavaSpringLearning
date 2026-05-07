package tacocloud.tacocloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tacocloud.tacocloud.dto.UserDto;
import tacocloud.tacocloud.service.AuthService;

@RestController
@RequestMapping("/auth/")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register/")
    public ResponseEntity<UserDto> register(@RequestBody UserDto user) {
        return ResponseEntity.ok(authService.registerUser(user));
    }

    @PostMapping("/login/")
    public ResponseEntity<String> login(@RequestBody UserDto user) {
        String token = authService.loginUser(user);
        return ResponseEntity.ok(token);
    }
}
