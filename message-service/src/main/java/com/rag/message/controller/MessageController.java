package com.rag.message.controller;


import com.rag.common.dto.MessageDto;
import com.rag.common.model.Sender;
import com.rag.message.service.MessageService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions/{sessionId}/messages")
public class MessageController {

    private final MessageService service;

    @Autowired
    public MessageController(MessageService service) { this.service = service; }

    public static class CreateMessageRequest {
        @NotBlank
        public String sender;
        @NotBlank
        public String content;
        public String context;
    }

    @PostMapping
    public ResponseEntity<MessageDto> addMessage(@PathVariable UUID sessionId,
                                                 @RequestBody CreateMessageRequest req) {
        Sender s = Sender.valueOf(req.sender);
        MessageDto dto = service.addMessage(sessionId, s, req.content, req.context);
        return ResponseEntity.status(201).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<MessageDto>> list(@PathVariable UUID sessionId,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(service.listMessages(sessionId, page, size));
    }
}
