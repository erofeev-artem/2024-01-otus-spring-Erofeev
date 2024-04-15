package ru.otus.hw.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ConsoleCommands {
    @ShellMethod(value = "Start console", key = "console")
    public void openConsole()  {
//        org.h2.tools.Console.main();
    }
}
