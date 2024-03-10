package ru.otus.hw;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.service.StreamsIOService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LocalizedIOServiceImplTest {

    private StreamsIOService ioService;
    private PrintStream backup;
    private ByteArrayOutputStream bos;

    @BeforeEach
    void init() {
        backup = System.out;
        bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));
        ioService = new StreamsIOService(new PrintStream(System.out), System.in);
    }

    @AfterEach
    void tearDown() {
        System.setOut(backup);
    }

    @Test
    void printLine() throws InterruptedException {
        ioService.printLine("текст для проверки");
        Thread.sleep(1000);
        assertThat(bos.toString()).isEqualTo("текст для проверки" + System.lineSeparator());
    }
}
