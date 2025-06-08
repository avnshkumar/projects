package com.example.components.commands;

import com.example.components.Command;
import com.example.constants.CommandName;
import com.example.services.ParkingService;

public class ExitCommandExecutor extends CommandExecutor {

    private final ParkingService parkingService;
    public static final String COMMAND_NAME = CommandName.EXIT;

    public ExitCommandExecutor(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @Override
    public void execute(Command command){
        System.out.println(COMMAND_NAME + "Exiting the parking system. Good bye!!");

    }
}
