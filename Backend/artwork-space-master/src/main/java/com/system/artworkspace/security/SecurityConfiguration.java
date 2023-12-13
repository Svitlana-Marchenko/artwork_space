package com.system.artworkspace.security;

import com.system.artworkspace.security.auth.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;


@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
//todo security for rating
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                            config.setAllowedMethods(Collections.singletonList("*"));
                            config.setAllowedHeaders(Collections.singletonList("*"));
                            return config;
                        })
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/users/**", "/artworks/**", "/auctions/**", "/exhibitions/**", "/collections/**", "/collectioneer/auctions/**", "/signin/**"))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.POST, "/exhibitions/**").hasAuthority("CURATOR")
                        .requestMatchers(HttpMethod.PUT, "/exhibitions/**").hasAuthority("CURATOR")
                        .requestMatchers(HttpMethod.DELETE, "/exhibitions/**").hasAuthority("CURATOR")
                        .requestMatchers(HttpMethod.POST, "/auctions/**").hasAuthority("ARTIST")
                        .requestMatchers(HttpMethod.PUT, "/auctions/**").hasAuthority("ARTIST")
                        .requestMatchers(HttpMethod.DELETE, "/auctions/**").hasAuthority("ARTIST")
//                        .requestMatchers(HttpMethod.POST, "/artworks/{id}/addRating").hasAuthority("CURATOR")
//                        .requestMatchers(HttpMethod.DELETE, "/artworks/{id}/deleteRating").hasAuthority("CURATOR")
                        .requestMatchers(HttpMethod.POST, "/artworks/**").hasAnyAuthority("ARTIST", "CURATOR")
                        .requestMatchers(HttpMethod.PUT, "/artworks/**").hasAuthority("ARTIST")
                        .requestMatchers(HttpMethod.DELETE, "/artworks/**").hasAnyAuthority("ARTIST", "CURATOR")
                        .requestMatchers("/users/new").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/**").hasAnyAuthority("COLLECTIONEER", "ARTIST", "CURATOR")
                        .requestMatchers(HttpMethod.PUT, "/users/**").hasAnyAuthority("COLLECTIONEER", "ARTIST", "CURATOR")
                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasAnyAuthority("COLLECTIONEER", "ARTIST", "CURATOR")
                        .requestMatchers(HttpMethod.POST, "/collectioneer/auctions/**").hasAuthority("COLLECTIONEER")
                        .requestMatchers(HttpMethod.PUT, "/collectioneer/auctions/**").hasAuthority("COLLECTIONEER")
                        .requestMatchers(HttpMethod.DELETE, "/collectioneer/auctions/**").hasAuthority("COLLECTIONEER")
                        .anyRequest().permitAll()
                ).sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                )
                .httpBasic(Customizer.withDefaults())
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

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

}
