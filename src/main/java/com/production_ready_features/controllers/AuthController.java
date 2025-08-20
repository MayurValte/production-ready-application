package com.production_ready_features.controllers;

import com.production_ready_features.DTO.LoginDTO;
import com.production_ready_features.DTO.LoginResponseDto;
import com.production_ready_features.DTO.SignUpDTO;
import com.production_ready_features.DTO.UserDTO;
import com.production_ready_features.entities.User;
import com.production_ready_features.services.AuthService;
import com.production_ready_features.services.JwtService;
import com.production_ready_features.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    //    private JwtService jwtService;
    private final UserService userService;
    private final AuthService authService;

    @Value("${deploy.env}")
    private String deployEnv;

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
        UserDTO userDTO = userService.signUp(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        LoginResponseDto tokenDto = authService.login(loginDTO);
        Cookie cookie = new Cookie("refreshToken", tokenDto.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv));
        response.addCookie(cookie);
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies()).
                filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the Cookies"));
        LoginResponseDto loginResponseDto = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(loginResponseDto);
    }

}
