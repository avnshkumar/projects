package com.example.dao;

import com.example.documents.ParkingLotDocument;
import com.example.documents.ParkingSlot;

public class ParkingLotRepository {
    private ParkingLotDocument parkingLotDocument;

    public ParkingLotRepository(ParkingLotDocument parkingLotDocument) {
        this.parkingLotDocument = parkingLotDocument;
    }

    public ParkingLotDocument getParkingSlotDocument() {
        return parkingLotDocument;
    }

    public boolean hasEmptySlots() {
        return parkingLotDocument.getAvailableSlots() > 0;
    }

    public ParkingSlot findFirstEmptySlot() {
        for (ParkingSlot slot : parkingLotDocument.getParkingSlots()) {
            if (slot.isAvailable()) {
                return slot; // Return the first available slot
            }
        }
        return null; // No empty slots found
    }

}
