package com.example.components.commands;

import com.example.components.Command;
import com.example.constants.CommandName;
import com.example.services.ParkingService;

import java.util.UUID;

public class CreateParkingLot extends CommandExecutor {
    private final ParkingService parkingService;
    public static final String COMMAND_NAME = CommandName.CREATE_PARKING_LOT;

    public CreateParkingLot(ParkingService parkingService) {
        this.parkingService = parkingService;
    }
    @Override
    public void execute(Command command) throws Exception {
        if (command.getArgs().isEmpty()) {
            throw new IllegalArgumentException("Please provide the number of slots.");
        }

        try {
            int numberOfSlots = Integer.parseInt(command.getArgs().getFirst());
            String name = "Avinash Parking Lot";
            parkingService.createParkingLot(UUID.randomUUID().toString(), name,numberOfSlots);
            System.out.println("Created a " +  name + " with " + numberOfSlots + " slots.");
            return;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number of slots: " + command.getArgs().getFirst());
        }
    }
}
