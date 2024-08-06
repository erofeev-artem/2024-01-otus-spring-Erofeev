package ru.otus.order_processing.service;

import ru.otus.order_processing.dto.UserDto;

import java.util.List;

public interface UserService {

    public List<UserDto> findByRoleId(long roleId);
}
