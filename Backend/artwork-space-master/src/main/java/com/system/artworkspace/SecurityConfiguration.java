package com.system.artworkspace;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/users/**"))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/users/**").permitAll()
                        //.requestMatchers("/collectioneer/auctions/**").hasAuthority("COLLECTIONEER")
                        .requestMatchers("/collections/**").hasAuthority("COLLECTIONEER")
                        .requestMatchers("/auctions/**").hasAuthority("ARTIST")
                        .requestMatchers(HttpMethod.POST, "/exhibitions/**").hasAuthority("CURATOR")
                        .requestMatchers(HttpMethod.PUT, "/exhibitions/**").hasAuthority("CURATOR")
                        .requestMatchers(HttpMethod.DELETE, "/exhibitions/**").hasAuthority("CURATOR")
                        .anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
        .logout(logout -> logout
              .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .permitAll()
        );
        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {

        return new UserInfoUserDetailsService();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

}
