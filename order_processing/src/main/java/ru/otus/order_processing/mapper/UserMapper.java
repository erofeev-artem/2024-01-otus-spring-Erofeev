package ru.otus.order_processing.mapper;

import org.mapstruct.Mapper;
import ru.otus.order_processing.dto.UserDto;
import ru.otus.order_processing.model.User;

@Mapper
public interface UserMapper {
    UserDto entityToDto(User entity);

    User dtoToEntity(UserDto dto);
}
