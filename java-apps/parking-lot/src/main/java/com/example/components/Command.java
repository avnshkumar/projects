package com.example.components;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Command {
    private String name;
    private List<String> args;

    public static Command parse(String commandLine) {
        String[] parts = commandLine.trim().split("\\s+");
        String name = parts[0];
        List<String> args = List.of(parts).subList(1, parts.length);
        return new Command(name, args);
    }
}
