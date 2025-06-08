package com.example.documents;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParkingSlot {
    int id;
    boolean isAvailable = false;
    Vehicle parkedVehicle;

}
