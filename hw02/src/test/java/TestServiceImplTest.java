import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.service.TestServiceImpl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestServiceImplTest {
    Student studentMock;
    TestResult testResultMock;
    TestServiceImpl testServiceImplMock;

    @BeforeEach
    void initMocks(){
        studentMock = mock(Student.class);
        testResultMock = mock(TestResult.class);
        testServiceImplMock = mock(TestServiceImpl.class);
    }
    @Test
    void showResultAnsweredCount() {
        when(testResultMock.getRightAnswersCount()).thenReturn(3);
        when(testServiceImplMock.executeTestFor(studentMock)).thenReturn(testResultMock);
        int rightAnswerCount = testServiceImplMock.executeTestFor(studentMock).getRightAnswersCount();
        Assertions.assertEquals(3, rightAnswerCount);
    }
}
