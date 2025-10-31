package com.rag.message.service;


import com.rag.common.dto.MessageDto;
import com.rag.common.model.Sender;
import com.rag.message.entity.MessageEntity;
import com.rag.message.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageService {

    private final MessageRepository repo;

    @Autowired
    public MessageService(MessageRepository repo) {
        this.repo = repo;
    }

    public MessageDto addMessage(UUID sessionId, Sender sender, String content, String context) {
        MessageEntity e = new MessageEntity(sessionId, sender, content, context);
        e = repo.save(e);
        return toDto(e);
    }

    public Page<MessageDto> listMessages(UUID sessionId, int page, int size) {
        Pageable p = PageRequest.of(page, size, Sort.by("createdAt").ascending());
        return repo.findBySessionIdOrderByCreatedAtAsc(sessionId, p).map(this::toDto);
    }

    private MessageDto toDto(MessageEntity e) {
        return new MessageDto(e.getId(), e.getSessionId(), e.getSender(), e.getContent(), e.getContext(), e.getCreatedAt());
    }
}
