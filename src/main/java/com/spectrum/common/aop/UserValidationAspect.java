package com.spectrum.common.aop;

import com.spectrum.exception.user.UserNotFoundException;
import com.spectrum.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class UserValidationAspect {

    private final UserRepository userRepository;

    @Before("@annotation(com.spectrum.common.aop.UserValidation)")
    public void validateUser(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long id = (Long) args[0];
        if (id == null || userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException();
        }
    }
}
