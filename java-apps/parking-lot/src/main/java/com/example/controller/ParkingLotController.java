package com.example.controller;

import com.example.documents.ParkingLotDocument;
import com.example.services.ParkingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="parking-slot")
public class ParkingLotController {

    private ParkingLotDocument parkingLotDocument;
    private ParkingService parkingService;
    public ParkingLotController() {
        this.parkingLotDocument = new ParkingLotDocument("1", "Main Parking Lot", 0); // Example initialization
        this.parkingService = new ParkingService(parkingLotDocument);
    }

    @PostMapping("/start-live-session")
    public void startLiveSession() {
        // Logic to start a live session
        // This could involve initializing the parking lot, setting up the service, etc.
        System.out.println("Live session started with parking lot: " + parkingLotDocument.getName());
    }




}
