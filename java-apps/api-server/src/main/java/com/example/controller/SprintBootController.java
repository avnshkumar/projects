package com.example.controller;

import com.example.apiserver.ApiServerApplication;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController()
@RequestMapping("/spring-boot")
public class SprintBootController {
    ApplicationContext applicationContext;


    public SprintBootController(ApplicationContext context) {
        this.applicationContext = context;

    }

    @GetMapping("/hi")
    // this is high method
    public ResponseEntity<String> hi() {
        return ResponseEntity.ok("HI");
    }

    @GetMapping("/debug-application-context")
    public ResponseEntity<String> debugApplicationContext() {
        applicationContext.getBean(ApiServerApplication.class); // add debugger here
        log.info(applicationContext.getBean(ApiServerApplication.class));
        return ResponseEntity.ok("Debugging application context");
    }

    @GetMapping("get-all-current-thread")
    public ResponseEntity<String> getAllCurrentThread() {
        Thread[] threads = new Thread[Thread.activeCount()];
        Thread.enumerate(threads);
        StringBuilder threadNames = new StringBuilder();
        for (Thread thread : threads) {
            if (thread != null) {
                threadNames.append(thread.getName()).append("\n");
            }
        }
        return ResponseEntity.ok(threadNames.toString());
    }
}