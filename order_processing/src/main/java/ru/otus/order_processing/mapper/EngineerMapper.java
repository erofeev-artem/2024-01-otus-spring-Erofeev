package ru.otus.order_processing.mapper;

import org.mapstruct.Mapper;
import ru.otus.order_processing.dto.EngineerDto;
import ru.otus.order_processing.model.Engineer;

@Mapper
public interface EngineerMapper {
    EngineerDto entityToDto(Engineer entity);

    Engineer dtoToEntity(EngineerDto dto);
}
