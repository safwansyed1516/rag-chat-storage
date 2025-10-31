package com.rag.common.dto;
import com.rag.common.model.Sender;
import java.time.Instant;
import java.util.UUID;
public record MessageDto(UUID id, UUID sessionId, Sender sender, String content, String context, Instant createdAt) {}
