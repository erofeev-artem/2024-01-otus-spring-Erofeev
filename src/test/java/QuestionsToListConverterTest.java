import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.exceptions.QuestionReadException;
import ru.otus.hw.service.QuestionsToListConverter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QuestionsToListConverterTest {
    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void convertQuestionReadException() {
        QuestionsToListConverter questionsToListConverter = mock(QuestionsToListConverter.class);
        Resource resource = mock(Resource.class);
        Mockito.when(questionsToListConverter.convert(resource)).thenThrow(QuestionReadException.class);
        Assertions.assertThrows(QuestionReadException.class, () -> questionsToListConverter.convert(resource));
    }

    @Test
    void convertResourceFile() {
        AppProperties appProperties = mock(AppProperties.class);
        when(appProperties.getTestFileName()).thenReturn("questions.csv");
        var questionList = new QuestionsToListConverter()
                .convert(new ClassPathResource(appProperties.getTestFileName()));
        Assertions.assertEquals(5, questionList.size());
    }
}