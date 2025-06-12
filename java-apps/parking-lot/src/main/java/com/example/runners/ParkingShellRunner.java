package com.example.runners;

import com.example.components.Command;
import com.example.components.commands.CommandExecutor;
import com.example.components.commands.CommandExecutorFactory;
import com.example.constants.CommandName;
import com.example.services.ParkingService;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class ParkingShellRunner {
    private Runnable runnable = () -> {
        ParkingService parkingService = new ParkingService(null);
        CommandExecutorFactory commandExecutorFactory = new CommandExecutorFactory(parkingService);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Starting on thread: " + Thread.currentThread().getName() + "\n");
        System.out.println("Welcome to the Parking Lot Management System!");
        while (true) {
            System.out.print("Running on thread: " + Thread.currentThread().getName() + "\n");
            String input = scanner.nextLine().trim();
            Command command = Command.parse(input);
            CommandExecutor commandExecutor = commandExecutorFactory.getCommandExecutor(command.getName());
            try {
                commandExecutor.execute(command);
                if (command.getName().equals(CommandName.EXIT)) {
                    return;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    };

    public void start() {
        Thread thread = new Thread(runnable);
        thread.start();
    }

}
