package com.example.controller;

import com.example.documents.ParkingLotDocument;
import com.example.runners.ParkingShellRunner;
import com.example.services.ParkingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="parking-slot")
public class ParkingLotController {
    private final ParkingShellRunner parkingShellRunner;

    public ParkingLotController(ParkingShellRunner parkingShellRunner) {
        this.parkingShellRunner = parkingShellRunner;
    }

    @PostMapping("/start-live-session")
    public void startLiveSession() {
        parkingShellRunner.start();
    }




}
