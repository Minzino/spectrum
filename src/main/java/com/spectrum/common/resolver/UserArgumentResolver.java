package com.spectrum.common.resolver;

import com.spectrum.auth.provider.JwtProvider;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private final static String AUTHORIZATION_HEADER = "Authorization";
    private final static String TOKEN_TYPE = "Bearer ";

    private final JwtProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        HttpServletRequest request = Optional.ofNullable(
                webRequest.getNativeRequest(HttpServletRequest.class))
            .orElseThrow(NullPointerException::new);

        String token = parseAuthorizationHeader(request);
        if (!jwtProvider.validateToken(token)) {
            throw new IllegalArgumentException();
        }

        String payload = jwtProvider.getPayload(token);
        long userId;
        try {
            userId = Long.parseLong(payload);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
        return userId;
    }

    private static String parseAuthorizationHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (Objects.isNull(authorizationHeader) || !authorizationHeader.startsWith(TOKEN_TYPE)) {
            throw new IllegalArgumentException();
        }

        return authorizationHeader.substring(TOKEN_TYPE.length());
    }
}
