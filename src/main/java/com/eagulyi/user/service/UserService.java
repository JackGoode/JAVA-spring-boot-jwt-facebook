package com.eagulyi.user.service;

import com.eagulyi.security.auth.ajax.LoginRequest;
import com.eagulyi.user.entity.User;

/**
 * Created by eugene on 4/16/17.
 */
public interface UserService {
    User getByUsername(String username);

    User getOrCreateUser(String username);

    User createDefaultUser(User user);

    User createDefaultUser(LoginRequest request);

    void save(User user);
}
