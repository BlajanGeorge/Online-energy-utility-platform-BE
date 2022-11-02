package com.onlineenergyutilityplatform.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.onlineenergyutilityplatform.utilities.Constants.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private static final String ADMINISTRATOR = "ADMINISTRATOR";
    private static final String CLIENT = "CLIENT";
    private static final String WILD_CARD = "/**";

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, USER_BY_ID).hasAnyRole(CLIENT, ADMINISTRATOR)
                .antMatchers(HttpMethod.DELETE, DEVICE_TO_USER).hasAnyRole(CLIENT, ADMINISTRATOR)
                .antMatchers(HttpMethod.PUT, UNNASIGNED_DEVICE_TO_USER).hasAnyRole(CLIENT, ADMINISTRATOR)
                .antMatchers(ENERGY_REPORTS_FOR_DEVICE).hasAnyRole(CLIENT, ADMINISTRATOR)
                .antMatchers(HttpMethod.POST, USERS_CREATE_CLIENT).permitAll()
                .antMatchers(USERS + WILD_CARD).hasRole(ADMINISTRATOR)
                .antMatchers(DEVICES + WILD_CARD).hasRole(ADMINISTRATOR)
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated();

        return http.build();
    }
}
