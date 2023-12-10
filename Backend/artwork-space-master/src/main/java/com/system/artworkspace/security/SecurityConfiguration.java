package com.system.artworkspace.security;


import com.system.artworkspace.security.auth.JwtAuthenticationFilter;
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
    //todo check security

//    @Autowired
//    private JwtAuthenticationFilter jwtAuthFilter;
//
//    @Autowired
//    private AuthenticationProvider authenticationProvider;
//

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

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
                                //.requestMatchers("/collectioneer/auctions/**").hasAuthority("COLLECTIONEER")
                                .requestMatchers("/signin").permitAll()
                                .requestMatchers("/collections/**").hasAuthority("COLLECTIONEER")
                                .requestMatchers("/auctions/**").hasAuthority("ARTIST")
//                        .requestMatchers(HttpMethod.POST, "/exhibitions/**").hasAuthority("CURATOR")
//                        .requestMatchers(HttpMethod.PUT, "/exhibitions/**").hasAuthority("CURATOR")
//                        .requestMatchers(HttpMethod.DELETE, "/exhibitions/**").hasAuthority("CURATOR")
                                //todo fix permitAll() for artwork
                                .requestMatchers("/artworks/**").permitAll()
                                //todo fix permitAll() for auction
                                .requestMatchers(HttpMethod.GET, "/collectioneer/auctions/**").permitAll()
                                //todo fix permitAll() for exhibition
                                .requestMatchers("/exhibitions/**").permitAll()
                                //todo fix permitall for user
                                .requestMatchers("/users/**").permitAll()
                                .anyRequest().authenticated()
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
