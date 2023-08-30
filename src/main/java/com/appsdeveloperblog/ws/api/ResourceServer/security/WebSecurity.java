package com.appsdeveloperblog.ws.api.ResourceServer.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurity {

    @Bean
    SecurityFilterChain configure(HttpSecurity security) throws Exception {
        JwtAuthenticationConverter jwtAuthenticationConverter =
                new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConvertor());
        security.authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.GET, "/users/status/check")
                        .hasRole("developer")
//                        .hasAuthority("SCOPE_profile")
                        .anyRequest()
                        .authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}).jwt().jwtAuthenticationConverter(jwtAuthenticationConverter));
        return security.build();
    }
}
