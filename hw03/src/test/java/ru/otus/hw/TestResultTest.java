package ru.otus.hw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

public class TestResultTest {
    @Mock
    Student student;
    @Mock
    Question question;

    @Test
    void getRightAnswerCount() {
        TestResult testResult = new TestResult(student);
        testResult.applyAnswer(question,true);
        testResult.applyAnswer(question,true);
        testResult.applyAnswer(question,true);
        int rightAnswerCount = testResult.getRightAnswersCount();
        Assertions.assertEquals(3, rightAnswerCount);
    }
}
