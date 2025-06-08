package com.example.services;

import com.example.documents.ParkingLotDocument;
import com.example.documents.ParkingSlot;
import com.example.documents.Vehicle;
import com.example.exceptions.NoEmptySlotException;
import com.example.exceptions.ResourceNotFoundException;
import lombok.Setter;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Setter
public class ParkingService {
    private ParkingLotDocument parkingLotDocument;
    public ParkingService(ParkingLotDocument parkingLotDocument) {
        this.parkingLotDocument = parkingLotDocument;
    }

    public void createParkingLot(String id, String name, int totalSlots) {
        if (parkingLotDocument != null) {
            throw new IllegalStateException("Parking lot already exists");
        }
        parkingLotDocument = new ParkingLotDocument(id, name, totalSlots);
    }
    public boolean hasEmptySlots() {
        return parkingLotDocument.getAvailableSlots() > 0;
    }

    public boolean isValidSlotId(int slotId){
        return slotId > 0 && slotId <= parkingLotDocument.getTotalSlots();
    }

    public ParkingSlot getParkingSlot(int slotId) {
        if (!isValidSlotId(slotId)){
            throw new IllegalArgumentException("Invalid slot ID: " + slotId);
        }
        return parkingLotDocument.getParkingSlots().get(slotId-1);
    }

    public ParkingSlot findFirstEmptyParkingSlot() throws NoEmptySlotException {
        if (!hasEmptySlots()) {
            throw new NoEmptySlotException("No available parking slots found");
        }
        for (ParkingSlot slot : parkingLotDocument.getParkingSlots()) {
            if (slot.isAvailable()) {
                return slot; // Return the first available slot
            }
        }
        throw new NoEmptySlotException("No available parking slots found");
    }

    public void parkVehicle(ParkingSlot slot,Vehicle vehicle)  throws NoEmptySlotException {
        if (slot.isAvailable()) {
            slot.setAvailable(false);
            slot.setParkedVehicle(vehicle);
            parkingLotDocument.setAvailableSlots(parkingLotDocument.getAvailableSlots() - 1);
        } else {
            throw new IllegalStateException("Slot is already occupied");
        }
    }

    public void findEmptySlotAndParkVehicle(Vehicle vehicle) throws NoEmptySlotException {
        ParkingSlot slot = findFirstEmptyParkingSlot();
        parkVehicle(slot, vehicle);
    }

    public void unparkVehicle(ParkingSlot slot)  throws NoEmptySlotException {
        if (slot == null || slot.isAvailable()) {
            throw new NoEmptySlotException("Slot is already empty or does not exist");
        }
        slot.setParkedVehicle(null);
        slot.setAvailable(true);
        parkingLotDocument.setAvailableSlots(parkingLotDocument.getAvailableSlots() + 1); // Make it atomic operation for thread safe
    }

    public void unparkVehicle(int slotId) throws IllegalArgumentException, RuntimeException  {
        ParkingSlot parkingSlot = getParkingSlot(slotId);
        unparkVehicle(parkingSlot);
    }


    public void unparkVehicle(String registrationNumber){
        ParkingSlot parkingSlot = findParkingSlotByVehicleRegistrationNumber(registrationNumber);
        unparkVehicle(parkingSlot);
    }

    public ArrayList<Vehicle> getAllParkedVehicles(){
        return parkingLotDocument.getParkingSlots().stream()
                .filter(parkingSlot -> !parkingSlot.isAvailable() && parkingSlot.getParkedVehicle()!=null)
                .map(ParkingSlot::getParkedVehicle)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Vehicle> getParkedVehiclesByColor(String color) {
        return findAllParkingSlotsByColor(color).stream()
                .map(ParkingSlot::getParkedVehicle)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ParkingSlot findParkingSlotByVehicleRegistrationNumber(String registrationNumber){
        for ( ParkingSlot parkingSlot : parkingLotDocument.getParkingSlots()){
            if (parkingSlot.isAvailable() && parkingSlot.getParkedVehicle()!=null && parkingSlot.getParkedVehicle().getRegistrationNumber().equals(registrationNumber)) {
                return parkingSlot;
            }
        }
        throw new ResourceNotFoundException("No vehicle found with registration number: " + registrationNumber);
    }

    public ArrayList<ParkingSlot> findAllParkedSlots() {
        return parkingLotDocument.getParkingSlots().stream()
                .filter(slot -> !slot.isAvailable())
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<ParkingSlot> findAllParkingSlotsByColor(String color){
        return findAllParkedSlots().stream()
                .filter(slot -> slot.getParkedVehicle() != null && slot.getParkedVehicle().getColor().equalsIgnoreCase(color))
                .collect(Collectors.toCollection(ArrayList::new));
    }


}
