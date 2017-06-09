package com.eagulyi.user.repository;

import com.eagulyi.user.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(@Param("username") String username);
    Optional<User> findByFacebookId(String userId);
    @Transactional void deleteByUsername(@Param("username") String username);
}
