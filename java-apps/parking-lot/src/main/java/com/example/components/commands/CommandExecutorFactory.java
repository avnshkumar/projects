package com.example.components.commands;

import com.example.constants.CommandName;
import com.example.services.ParkingService;

public class CommandExecutorFactory {
    private ParkingService parkingService;

    public CommandExecutorFactory(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    public CommandExecutor getCommandExecutor(String commandName) {
        switch (commandName.toLowerCase()) {
            case CommandName.CREATE_PARKING_LOT -> {
                return new CreateParkingLot(parkingService);
            }
            case CommandName.EXIT -> {
                return new ExitCommandExecutor(parkingService);
            }
            default -> {
                throw new IllegalArgumentException("Unknown command: " + commandName);
            }
        }
    }

}
