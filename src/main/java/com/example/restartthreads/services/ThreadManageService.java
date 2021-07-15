package com.example.restartthreads.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ThreadManageService {
    private static final int THREAD_COUNT = 10;
    private final ThreadPoolExecutor executor;
    private final List<Integer> ids;
    private Collection<Future<?>> futures;
    private final ThreadRunnerService threadRunnerService;
    private static int resetOffset = 0;

    public ThreadManageService(ThreadRunnerService threadRunnerService) {
        this.threadRunnerService = threadRunnerService;
        this.ids = new ArrayList<>();
        this.executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        for (int i = 0; i < THREAD_COUNT; i++) {
            ids.add(i);
        }
    }

    @PostConstruct
    public void startThreads() {
        futures = ids.stream()
                .map(this::startSingleThread)
                .collect(Collectors.toList());
    }

    private Future<?> startSingleThread(int id) {
        Runnable runnable = () -> threadRunnerService.run(resetOffset + id);
        return executor.submit(runnable);
    }

    public void reset() {
        log.info("***** STOPPING OLD THREADS");
        futures.forEach(f -> f.cancel(true));
        resetOffset += 1000;
        log.info("***** STARTING NEW THREADS");
        startThreads();
        log.info("***** STARTED NEW THREADS");
    }
}
