package ru.otus.hw.service;

import org.springframework.core.io.Resource;

public interface ResourceConverterService<T> {
    T convert(Resource resource);
}
