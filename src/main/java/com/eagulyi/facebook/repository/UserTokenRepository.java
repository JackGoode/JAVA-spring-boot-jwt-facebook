package com.eagulyi.facebook.repository;

import com.eagulyi.facebook.model.entity.UserToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by eugene on 5/8/17.
 */
@Repository
public interface UserTokenRepository extends CrudRepository<UserToken, String> {
    Optional<UserToken> findByUsername(String username);
    Optional<UserToken> findByToken(String token);
    Optional<UserToken> findByFbId(String fbId);

    void deleteByFbId(String fbId);
}
