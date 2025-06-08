package com.example.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingLotDocument {
    private String id;
    private String name;
    private int totalSlots;
    private int availableSlots;
    private ArrayList<ParkingSlot> parkingSlots;

    public ParkingLotDocument(String id, String name, int totalSlots) {
        this.id = id;
        this.name = name;
        this.totalSlots = totalSlots;
        this.availableSlots = totalSlots; // Initially all slots are available
        this.parkingSlots = new ArrayList<>(totalSlots);
        for (int i = 0; i < totalSlots; i++) {
            parkingSlots.add(new ParkingSlot(i + 1, true, null));
        }
    }
}
