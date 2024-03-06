package ru.otus.hw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@Service
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Autowired
    public TestServiceImpl(LocalizedIOService ioService, QuestionDao questionDao) {
        this.ioService = ioService;
        this.questionDao = questionDao;
    }

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            // Задать вопрос, получить ответ
            ioService.printLine(question.text());
            var isAnswerValid = checkAnswer(question);
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    private boolean checkAnswer(Question question) {
        int answerCounter = 0;
        for (Answer answer : question.answers()) {
            answerCounter++;
            ioService.printFormattedLine(answerCounter + ") " + answer.text());
        }
        int answerNumber = ioService.readIntForRangeLocalized(1, answerCounter,
                "TestService.answer.error.message");
        return question.answers().get(answerNumber - 1).isCorrect();
    }
}
