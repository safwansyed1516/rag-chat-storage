package com.rag.common.dto;
import java.time.Instant;
import java.util.UUID;
public record SessionDto(UUID id, String title, boolean favorite, Instant createdAt, Instant updatedAt) {}
