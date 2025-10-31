package com.rag.session.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name = "sessions")
@Getter @Setter
public class SessionEntity {
  @Id
  @Column(name = "id", nullable = false)
  private UUID id = UUID.randomUUID();

  @Column(nullable = false)
  private String title = "New Chat";

  @Column(nullable = false)
  private boolean favorite = false;

  @Column(nullable = false, updatable = false)
  private Instant createdAt = Instant.now();

  @Column(nullable = false)
  private Instant updatedAt = Instant.now();

  @PreUpdate
  void preUpdate() { this.updatedAt = Instant.now(); }
}
