package ru.otus.hw.service;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionsToListConverter implements ResourceConverterService<List<Question>> {

    @Override
    public List<Question> convert(Resource resource) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            ColumnPositionMappingStrategy<QuestionDto> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(QuestionDto.class);
            var csvToBean = new CsvToBeanBuilder<QuestionDto>(bufferedReader)
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
