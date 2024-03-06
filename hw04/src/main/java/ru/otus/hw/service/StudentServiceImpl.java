package ru.otus.hw.service;

import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Student;

@Service
public class StudentServiceImpl implements StudentService {

    @Override
    public Student determineCurrentStudent(String firstName, String lastName) {
        return new Student(firstName, lastName);
    }
}