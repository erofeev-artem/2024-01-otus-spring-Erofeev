package ru.otus.order_processing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.order_processing.dto.UserDto;
import ru.otus.order_processing.exception.EntityNotFoundException;
import ru.otus.order_processing.mapper.UserMapper;
import ru.otus.order_processing.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username)
                .map(u -> User.builder()
                        .username(u.getUserName())
                        .password(u.getPassword())
                        .roles(u.getRole().getName())
                        .build())
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("User with username %s not found", username)));
    }

    @Override
    public List<UserDto> findByRoleId(long roleId) {
        return userRepository.findByRoleId(roleId).stream().map(userMapper::entityToDto).toList();
    }
}
