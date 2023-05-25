package com.prokectB.meongbti.application.auth.token;

import com.prokectB.meongbti.exception.unauthorized.TokenMalformedException;
import com.prokectB.meongbti.exception.unauthorized.TokenNotExistsException;
import org.springframework.stereotype.Component;

@Component
public class AuthTokenExtractor {

    private static final String TOKEN_TYPE = "Bearer";

    public String extractToken(String authorizationHeader) {
        if (authorizationHeader == null) {
            throw new TokenNotExistsException();
        }

        String[] splitHeaders = authorizationHeader.split(" ");

        if (splitHeaders.length != 2 || !splitHeaders[0].equalsIgnoreCase(TOKEN_TYPE)) {
            throw new TokenMalformedException();
        }
        return splitHeaders[1];
    }
}