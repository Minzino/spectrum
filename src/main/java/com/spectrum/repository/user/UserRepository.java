package com.spectrum.repository.user;

import com.spectrum.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByOauthId(String id);
}
