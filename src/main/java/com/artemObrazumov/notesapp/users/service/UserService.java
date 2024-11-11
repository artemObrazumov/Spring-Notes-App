package com.artemObrazumov.notesapp.users.service;

import com.artemObrazumov.notesapp.users.entity.User;
import com.artemObrazumov.notesapp.users.exceptions.UserWithEmailExists;
import com.artemObrazumov.notesapp.users.exceptions.UserWithNameExists;
import com.artemObrazumov.notesapp.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User save(User user) {
        return repository.save(user);
    }

    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new UserWithNameExists();
        }

        if (repository.existsByEmail(user.getEmail())) {
            throw new UserWithEmailExists();
        }

        return save(user);
    }

    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username not found"));

    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return getByUsername(username);
    }
}
