package br.com.zrteam.managementapp.application.service.jwt;

import org.springframework.security.core.Authentication;

public interface JwtService {
    String generateToken(Authentication authentication);

    String getUsernameFromToken(String token);

    boolean validateToken(String token);

    String getClaimFromTokenByKey(String token,
                                  String key);
}
