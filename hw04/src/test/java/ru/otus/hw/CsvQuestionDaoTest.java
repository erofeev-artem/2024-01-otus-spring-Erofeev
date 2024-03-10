package ru.otus.hw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class CsvQuestionDaoTest {

    @Autowired
    CsvQuestionDao csvQuestionDao;

    @Mock
    CsvQuestionDao csvQuestionDaoMock;

    @Test
    void findAll() {
        List<Question> questions = csvQuestionDao.findAll();
        Assertions.assertEquals(5, questions.size());
    }

    @Test
    void findAllInvokeOnce() {
        csvQuestionDaoMock.findAll();
        verify(csvQuestionDaoMock, atMostOnce()).findAll();
    }
}
