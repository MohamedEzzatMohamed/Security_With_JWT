package com.mezzat.security_with_jwt.domain.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class JwtUtils {

    private static final String SECRET = "secret";
    private static final Algorithm ALGORITHM =
            Algorithm.HMAC256(SECRET.getBytes());

    public static DecodedJWT getDecodedJwt(String token) {
        JWTVerifier verifier = JWT.require(ALGORITHM).build();
        return verifier.verify(token);
    }

    public static Collection<GrantedAuthority> getAuthorities(String[] roles) {

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        if (roles == null || roles.length == 0) {
            return authorities;
        }

        Arrays.stream(roles)
                .filter(Objects::nonNull)
                .forEach(role ->
                        authorities.add(new SimpleGrantedAuthority(role)));

        return authorities;
    }

    public static Algorithm getAlgorithm() {
        return ALGORITHM;
    }
}