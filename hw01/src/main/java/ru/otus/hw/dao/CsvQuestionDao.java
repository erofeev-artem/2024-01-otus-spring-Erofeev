package ru.otus.hw.dao;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        ClassPathResource resource = new ClassPathResource(fileNameProvider.getTestFileName());
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            final ColumnPositionMappingStrategy<QuestionDto> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(QuestionDto.class);
            CsvToBean<QuestionDto> csvToBean = new CsvToBeanBuilder<QuestionDto>(bufferedReader)
                    .withSeparator(';')
                    .withMappingStrategy(strategy)
                    .withSkipLines(1)
                    .build();
            return csvToBean.stream().map(QuestionDto::toDomainObject).collect(Collectors.toList());
        } catch (IOException e) {
            throw new QuestionReadException("Could not read the question file", e);
        }
    }
}
