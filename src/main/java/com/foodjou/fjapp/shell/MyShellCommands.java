package com.foodjou.fjapp.shell;


import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class MyShellCommands {

    @ShellMethod("Say hello")
    public String sayHello(String name) {
        return "Hello, Spring Shell!" + name;
    }
}
