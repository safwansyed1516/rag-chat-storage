package com.rag.common.dto;
import jakarta.validation.constraints.NotBlank;
public record CreateSessionRequest(@NotBlank String title) {}
