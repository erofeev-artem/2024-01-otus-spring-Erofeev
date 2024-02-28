package ru.otus.hw.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import ru.otus.hw.domain.Question;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.service.QuestionsToListConverter;

import java.util.List;

@Component
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    private final QuestionsToListConverter converter;

    @Autowired
    public CsvQuestionDao(TestFileNameProvider fileNameProvider, QuestionsToListConverter converter) {
        this.fileNameProvider = fileNameProvider;
        this.converter = converter;
    }

    @Override
    public List<Question> findAll() {
        ClassPathResource resource = new ClassPathResource(fileNameProvider.getTestFileName());
        return converter.convert(resource);
    }
}
