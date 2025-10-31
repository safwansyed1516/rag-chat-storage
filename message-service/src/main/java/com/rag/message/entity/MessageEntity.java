package com.rag.message.entity;

import com.rag.common.model.Sender;
import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name="messages")
@Getter @Setter
public class MessageEntity {
  @Id
  @Column(nullable=false)
  private UUID id = UUID.randomUUID();

  @Column(nullable=false)
  private UUID sessionId;

  @Enumerated(EnumType.STRING)
  @Column(nullable=false)
  private Sender sender;

  @Column(nullable=false, columnDefinition="CLOB")
  private String content;

  @Column(columnDefinition="CLOB")
  private String context;

  @Column(nullable=false, updatable=false)
  private Instant createdAt = Instant.now();

    public MessageEntity(UUID sessionId, Sender sender, String content, String context) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.sessionId = sessionId;
        this.sender = sender;
        this.content = content;
        this.context = context;
    }

}
