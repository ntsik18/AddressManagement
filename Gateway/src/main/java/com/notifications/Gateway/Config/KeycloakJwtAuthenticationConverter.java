package com.notifications.Gateway.Config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {
    @Override
    public Mono<AbstractAuthenticationToken> convert(@NonNull Jwt source) {
        return Mono.just(new JwtAuthenticationToken(
                source,
                Stream.concat(
                        new JwtGrantedAuthoritiesConverter().convert(source).stream(),
                        extractRoles(source).stream()
                ).collect(toSet()
                )));
    }

    private Collection<? extends GrantedAuthority> extractRoles(Jwt jwt) {
        Collection<GrantedAuthority> authorities = new HashSet<>();

        authorities.addAll(extractRealmRoles(jwt));
        authorities.addAll(extractResourceRoles(jwt));

        return authorities;
    }

    private Collection<? extends GrantedAuthority> extractRealmRoles(Jwt jwt) {
        var realmAccess = (Map<String, List<String>>) jwt.getClaim("realm_access");
        var roles = realmAccess.get("roles");

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.replace("-", "_"))) // Prefix with ROLE_
                .collect(toSet());
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        var resourceAccess = (Map<String, Map<String, List<String>>>) jwt.getClaim("resource_access");
        String clientId = jwt.getClaimAsString("azp"); // Get the client ID dynamically

        if (resourceAccess != null) {
            var clientRoles = resourceAccess.get(clientId); // Use dynamic client ID
            if (clientRoles != null) {
                var roles = clientRoles.get("roles");
                return roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.replace("-", "_")))
                        .collect(toSet());
            }
        }

        return Set.of(); // Return an empty set if no roles found
    }
}
