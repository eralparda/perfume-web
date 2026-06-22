package com.eralparda.PerfumeWeb.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        return http
                .csrf(csrf-> csrf.disable())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex-> ex
                        .authenticationEntryPoint(jwtAuthEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler))
                .authorizeHttpRequests(auth->auth
                        //CONTROLLERA GÖRE VERİLİR
                        .requestMatchers("/auth/**").permitAll()

                        .requestMatchers(HttpMethod.GET,"/perfumes/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/notes/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/perfumes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/perfumes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/perfumes/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/categories/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/notes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/notes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/notes/**").hasRole("ADMIN")

                        .requestMatchers("/users/**").hasRole("ADMIN")

                        .requestMatchers("/orders/**").authenticated()

                        .requestMatchers("/cart/**").authenticated()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
