package com.example.runners;

import com.example.components.Command;
import com.example.components.commands.CommandExecutor;
import com.example.components.commands.CommandExecutorFactory;
import com.example.constants.CommandName;
import com.example.services.ParkingService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ParkingService parkingService = new ParkingService(null);
        CommandExecutorFactory commandExecutorFactory = new CommandExecutorFactory(parkingService);

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to the Parking Lot Management System!");
            while (true) {
                String input = scanner.nextLine().trim();
                Command command = Command.parse(input);
                CommandExecutor commandExecutor = commandExecutorFactory.getCommandExecutor(command.getName());
                try {
                    commandExecutor.execute(command);
                    if (command.getName().equals(CommandName.EXIT)) {
                        System.out.println("Exiting the system. Goodbye!");
                        break;
                    }
                } catch (Exception e) {
                    System.err.println("Error executing command: " + e.getMessage());
                }
            }
        }
    }
}