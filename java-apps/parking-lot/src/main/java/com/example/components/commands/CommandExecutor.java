package com.example.components.commands;

import com.example.components.Command;
import com.example.services.ParkingService;

public abstract class CommandExecutor {

    public abstract void execute(Command command) throws Exception;
}
