package com.system.artworkspace.security;

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
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;


@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {
    //todo check security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(Collections.singletonList("http://localhost:3000")); // Дозвіл на доступ з локального хоста 3000
                            config.setAllowedMethods(Collections.singletonList("*")); // Дозволені всі HTTP-методи
                            config.setAllowedHeaders(Collections.singletonList("*")); // Дозволені всі заголовки
                            return config;
                        })
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/users/**", "/artworks/**", "/auctions/**", "/exhibitions/**", "/collections/**", "/collectioneer/auctions/**"))
                .authorizeHttpRequests((authorize) -> authorize
                        //.requestMatchers("/users/**").permitAll()
                        //.requestMatchers("/collectioneer/auctions/**").hasAuthority("COLLECTIONEER")
                        .requestMatchers("/collections/**").hasAuthority("COLLECTIONEER")
                        .requestMatchers("/auctions/**").hasAuthority("ARTIST")
                        .requestMatchers(HttpMethod.POST, "/exhibitions/**").hasAuthority("CURATOR")
                        .requestMatchers(HttpMethod.PUT, "/exhibitions/**").hasAuthority("CURATOR")
                        .requestMatchers(HttpMethod.DELETE, "/exhibitions/**").hasAuthority("CURATOR")
                        //todo fix permitAll() for artwork
                        .requestMatchers(HttpMethod.GET, "/artworks/**").permitAll()
                        //todo fix permitAll() for auction
                        .requestMatchers(HttpMethod.GET, "/collectioneer/auctions/**").permitAll()
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
