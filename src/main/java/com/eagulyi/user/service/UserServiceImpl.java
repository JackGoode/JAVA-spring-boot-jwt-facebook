package com.eagulyi.user.service;

import com.eagulyi.security.auth.ajax.LoginRequest;
import com.eagulyi.user.entity.DataProvider;
import com.eagulyi.user.entity.User;
import com.eagulyi.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User createDefaultUser(User user) {
        user.setDefaultRoles();
        user.setCreationDate(LocalDateTime.now());
        System.out.println("Creating new user " + user.getUsername() + " timestamp: " + LocalDateTime.now());
        user = userRepository.save(user);
        System.out.println("Created new user " + user.getUsername() + " timestamp: " + LocalDateTime.now());
        return user;
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