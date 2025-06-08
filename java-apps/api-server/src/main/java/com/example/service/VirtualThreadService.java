package com.example.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class VirtualThreadService {
    /**
     * This is a list of fake speakers.
     * In a real application, this would be replaced with a database call or an external API call.
     */
    private final List<String> fakeSpeakers = IntStream.range(0, 100)
            .mapToObj(i -> "Fake Speaker " + i)
            .toList();

    /**
     * This method simulates a long-running task to fetch fake speakers.
     * It uses a sleep to simulate the delay.
     *
     * @return List of fake speakers
     */
    public List<String> getFakeSpeakers() {
        // Simulate a long-running task
        try {
            Thread.sleep(1000); // Simulate delay

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
        return fakeSpeakers;
    }
}
