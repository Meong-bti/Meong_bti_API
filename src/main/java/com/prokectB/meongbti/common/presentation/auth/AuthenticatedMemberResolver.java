package com.prokectB.meongbti.common.presentation.auth;

import com.prokectB.meongbti.authentication.token.TokenProvider;
import com.prokectB.meongbti.common.exception.unauthorized.TokenExpiredException;
import com.prokectB.meongbti.common.exception.unauthorized.TokenNotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthenticatedMemberResolver implements HandlerMethodArgumentResolver {

    private final TokenProvider tokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticatedMember.class);
    }



    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String authorizationHeader = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null) {
            throw new TokenNotExistsException();
        }
        if (tokenProvider.isValidToken(authorizationHeader)) {
            return tokenProvider.getmemberId(authorizationHeader);
        }
        throw new TokenExpiredException();
    }
}