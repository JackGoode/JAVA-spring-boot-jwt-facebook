package com.eagulyi.user.service;

import com.eagulyi.security.auth.ajax.LoginRequest;
import com.eagulyi.user.entity.DataProvider;
import com.eagulyi.user.entity.User;
import com.eagulyi.user.repository.UserRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public User getByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) throw new UsernameNotFoundException("user " + username + " not found");
        return userOptional.get();
    }

    @Override
    public User getOrCreateUser(String username) {
        return userRepository.findByUsername(username).orElseGet(() -> createDefaultUser(new User(username)));
    }

    @Override
    public User createDefaultUser(User user) {
        Assert.notNull(user.getUsername(), "Username should not be empty");
        user.setDefaultRoles();
        user.setCreationDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User createDefaultUser(LoginRequest request) {
        User user = new User();
        user.setPassword(encoder.encode(request.getPassword()));

        user.setUsername(request.getUsername());

        user.setDefaultRoles();
        user.setDataProvider(DataProvider.INTERNAL);
        return userRepository.save(user);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

}