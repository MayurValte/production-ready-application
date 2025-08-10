package com.production_ready_features.controllers;

import com.production_ready_features.DTO.LoginDTO;
import com.production_ready_features.DTO.SignUpDTO;
import com.production_ready_features.DTO.UserDTO;
import com.production_ready_features.entities.User;
import com.production_ready_features.services.AuthService;
import com.production_ready_features.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

//    private JwtService jwtService;

    private final AuthService authService;

/*    @GetMapping("/test")
    public Boolean testJwt() {
        User user = new User(10L, "mayur@gmail.com", "1234", "Mayur");
        String token = jwtService.generateToken(user);
        System.out.println(token);
        Long id = jwtService.getUserIdFromToke(token);
        System.out.println(id);
        return true;
    }*/

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO) {
        UserDTO userDTO = authService.signUp(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response){
        String token= authService.login(loginDTO);
        Cookie cookie=new Cookie("JWT_TOKEN",token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(token);
    }

}
