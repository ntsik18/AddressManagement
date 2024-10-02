package com.notifications.Gateway.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity

public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) throws Exception {
        serverHttpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                                .pathMatchers("/eureka/**","/notifications/swagger-ui.html").permitAll()
                                .pathMatchers("/notifications/admin/**").hasRole("ADMIN")
                                .pathMatchers("/notifications/customers/**",
                                        "/notifications/addresses/**",
                                        "/notifications/batchProcessor/**"
//                                        "/notifications/reports/**"
                                )
                                .hasAnyRole("ADMIN", "CUSTOMERAPI")
                                .pathMatchers("/notifications/notificationAddressService/**").hasRole("NOTIFICATIONAPI")
                                .anyExchange()
                                .authenticated()
                )
                .oauth2ResourceServer(auth ->
                        auth.jwt(token -> token.jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter())))
                .oauth2Login(withDefaults())
        ;

        return serverHttpSecurity.build();

    }
}





