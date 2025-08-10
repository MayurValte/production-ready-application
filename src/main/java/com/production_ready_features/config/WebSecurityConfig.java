package com.production_ready_features.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
                        .requestMatchers("/auth/login").permitAll()
                        .anyRequest().authenticated())
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig -> sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//                .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

/*    @Bean
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
    }*/

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
