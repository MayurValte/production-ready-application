package com.production_ready_features.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth ->auth
                        .requestMatchers("/posts/getPostById/**").permitAll()
                        .requestMatchers("/posts/createNewPost/**").hasAnyRole("ADMIN")
                        .requestMatchers("/posts/updatePost/**").hasAnyRole(("ADMIN"))
                        .requestMatchers("/posts/getAllPosts/**").hasAnyRole("USER")
                        .anyRequest().authenticated())
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig -> sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//                .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    UserDetailsService myInMemoryUserDetailService(){
        UserDetails normalUser= User
                .withUsername("Mayur")
                .password(passwordEncoder().encode("Mayur"))
                .roles("USER")
                .build();

        UserDetails adminUser= User
                .withUsername("Admin")
                .password(passwordEncoder().encode("Admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(normalUser,adminUser);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
