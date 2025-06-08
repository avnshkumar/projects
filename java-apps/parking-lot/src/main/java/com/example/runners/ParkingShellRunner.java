package com.example.runners;

import com.example.components.Command;
import com.example.components.commands.CommandExecutor;
import com.example.components.commands.CommandExecutorFactory;
import com.example.constants.CommandName;
import com.example.services.ParkingService;

import java.util.Scanner;

public class ParkingShellRunner implements Runnable {



    public static void main(String[] args) {
        ParkingShellRunner runner = new ParkingShellRunner();
        Thread thread = new Thread(runner);
        thread.start();
    }

    @Override
    public void run(){
        ParkingService parkingService = new ParkingService(null);
        CommandExecutorFactory commandExecutorFactory = new CommandExecutorFactory(parkingService);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Parking Lot Management System!");
        while(true){
            String input = scanner.nextLine().trim();
            Command command = Command.parse(input);
            CommandExecutor commandExecutor = commandExecutorFactory.getCommandExecutor(command.getName());
            try {
                commandExecutor.execute(command);
                if (command.getName().equals(CommandName.EXIT)){
                    return;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
