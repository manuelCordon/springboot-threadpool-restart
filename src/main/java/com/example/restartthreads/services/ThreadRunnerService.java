package com.example.restartthreads.services;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ThreadRunnerService {
    private final AtomicBoolean keepGoing = new AtomicBoolean(true);

    public void run(int id) {
        int count = 0;
        log.info("Running until interrupted task {}...", id);
        try {
            while (keepGoing.get()) {
                count++;
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException interruptedException) {
            log.info("Task {} interrupted after {} cycles.", id, count);

        } finally {
            log.info("Running finally statement task {}.", id);
        }
    }

    public void stop() {
        keepGoing.set(false);
    }
}
