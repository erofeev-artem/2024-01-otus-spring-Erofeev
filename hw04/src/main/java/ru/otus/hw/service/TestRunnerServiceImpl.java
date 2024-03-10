package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw.domain.Student;

@ShellComponent
@RequiredArgsConstructor
public class TestRunnerServiceImpl {

    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    private Student student;

    @ShellMethod(value = "1 enter the student's first and last name")
    public void login(@ShellOption String firstName, @ShellOption String lastName) {
        student = studentService.determineCurrentStudent(firstName, lastName);
    }

    @ShellMethod(value = "2 execute the test if the student has been registered")
    @ShellMethodAvailability("studentReady")
    public void execute() {
        var testResult = testService.executeTestFor(student);
        resultService.showResult(testResult);
    }

    public Availability studentReady() {
        return (student != null) ? Availability.available() : Availability.unavailable("Student doesn't exist");
    }
}
