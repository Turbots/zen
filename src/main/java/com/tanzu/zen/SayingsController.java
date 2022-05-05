package com.tanzu.zen;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SayingsController {
    
    private final MessageService messageService;

    public SayingsController(final MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/api/sayings")
    public ResponseEntity<String> getSayings() {
        return ResponseEntity.ok(messageService.getRandomSaying());
    }
}
