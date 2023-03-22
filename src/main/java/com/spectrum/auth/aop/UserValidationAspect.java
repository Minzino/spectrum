package com.spectrum.auth.aop;

import com.spectrum.exception.user.UserNotFoundException;
import com.spectrum.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class UserValidationAspect {

    private final UserRepository userRepository;

    @Before("@annotation(com.spectrum.auth.aop.UserValidation) && args(userId,..)")
    public void validateUser(Long userId) {
        if (userId == null || !userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }
    }
}
