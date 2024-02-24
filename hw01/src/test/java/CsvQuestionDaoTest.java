import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.exceptions.QuestionReadException;

import static org.mockito.Mockito.mock;

class CsvQuestionDaoTest {
    @Test
    void findAllQuestionReadException() {
        var csvQuestionDao = mock(CsvQuestionDao.class);
        Mockito.when(csvQuestionDao.findAll()).thenThrow(QuestionReadException.class);
        Assertions.assertThrows(QuestionReadException.class, csvQuestionDao::findAll);
    }
}
