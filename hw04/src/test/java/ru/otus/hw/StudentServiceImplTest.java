package ru.otus.hw;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.domain.Student;
import ru.otus.hw.service.StudentServiceImpl;

@SpringBootTest
public class StudentServiceImplTest {
    @Autowired
    StudentServiceImpl studentServiceImpl;

    @Test
    void determineCurrentStudent() {
        Student student = studentServiceImpl.determineCurrentStudent("name", "surname");
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(student.getFullName()).isEqualTo("name surname");
        softAssertions.assertThat(student.firstName()).isEqualTo("name");
        softAssertions.assertThat(student.lastName()).isEqualTo("surname");
        softAssertions.assertAll();
    }
}
