package com.omega.cowalk;

import com.omega.cowalk.domain.entity.user.Role;
import com.omega.cowalk.domain.entity.user.User;
import com.omega.cowalk.security.auth.PrincipalUserDetails;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@TestComponent
public class TestSecurityConfig implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(PrincipalUserDetails.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return new PrincipalUserDetails(User.builder()
                .id(1L)
                .identifier("user123")
                .password("user123!")
                .role(Role.USER)
                .build());
    }
}
