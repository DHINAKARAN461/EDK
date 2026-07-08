package com.edk.system.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth->
                                auth.requestMatchers(HttpMethod.GET,"/customer/log-in").permitAll()
                                        .requestMatchers(HttpMethod.POST,"/customer/sign-in").permitAll()
                                        .requestMatchers(HttpMethod.GET,"/customer/").permitAll()
                                        .requestMatchers(HttpMethod.GET,"/customer/listAll").authenticated()
                                        .requestMatchers(HttpMethod.GET,"/customer/getonpage").authenticated()
                                        .requestMatchers("/**").permitAll()
                );
        return httpSecurity.build();
    }
    @Bean
  public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService user(){
//
//    }
}
