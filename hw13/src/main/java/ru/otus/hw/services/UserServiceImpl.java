package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Role;
import ru.otus.hw.repositories.UserRepository;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username)
                .map(u -> User.builder()
                        .username(u.getUserName())
                        .password(u.getPassword())
                        .roles(u.getRole().stream().map(Role::getRole).toArray(String[]::new))
                        .build())
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("User with username %s not found", username)));
    }
}
