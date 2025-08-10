package com.production_ready_features.services;

import com.production_ready_features.DTO.LoginDTO;
import com.production_ready_features.DTO.SignUpDTO;
import com.production_ready_features.DTO.UserDTO;
import com.production_ready_features.entities.User;
import com.production_ready_features.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserDTO signUp(SignUpDTO signUpDTO){
        Optional<User> user = repository.findByEmail(signUpDTO.getEmail());
        if(user.isPresent()){
            throw new BadCredentialsException("User with email already exists "+signUpDTO.getEmail());
        }
        User toBeCreatedUser = modelMapper.map(signUpDTO, User.class);
        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));
        User savedUser = repository.save(toBeCreatedUser);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    public String login(LoginDTO loginDTO) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        User user= (User) authenticate.getPrincipal();
        return jwtService.generateToken(user);
    }
}
