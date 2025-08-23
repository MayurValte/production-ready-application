package com.production_ready_features.DTO;

import com.production_ready_features.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {
    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
}
