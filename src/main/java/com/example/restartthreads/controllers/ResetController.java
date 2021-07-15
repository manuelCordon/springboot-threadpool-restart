package com.example.restartthreads.controllers;

import com.example.restartthreads.services.ThreadManageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResetController {
    private final ThreadManageService threadManageService;

    public ResetController(ThreadManageService threadManageService) {
        this.threadManageService = threadManageService;
    }

    @PostMapping("threads/restart")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetThreads() {
        threadManageService.reset();
    }
}
