package com.example.controller;

import com.example.service.VirtualThreadService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;

@RestController
@RequestMapping(value="virtual-thread-test")
public class VirtualThreadController {

    Logger logger = LogManager.getLogger(VirtualThreadController.class);
    private final VirtualThreadService virtualThreadService;
    public static int counter = 0;

    public VirtualThreadController(VirtualThreadService virtualThreadService) {
        this.virtualThreadService = virtualThreadService;
    }

    @GetMapping("/speakers")
    public List<String> getSpeakers() {
        counter +=1;
        int t = counter;
        var startTime = System.currentTimeMillis();
        var result = virtualThreadService.getFakeSpeakers();
        var endTime = System.currentTimeMillis();
        logger.debug("Counter : {} Thread: {} - Time taken: {}ms", t, Thread.currentThread().getName(), endTime - startTime);
        return result;
    }

    @GetMapping("/too-many-threads")
    public void createTooManyThreads(){
        List<Thread> threads = IntStream.range(90000, 1000000).
                mapToObj(i -> Thread.ofVirtual().unstarted(() -> {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })).toList();
        threads.forEach(Thread::start);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
