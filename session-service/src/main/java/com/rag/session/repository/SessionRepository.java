package com.rag.session.repository;

import com.rag.session.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
public interface SessionRepository extends JpaRepository<SessionEntity, UUID> {}
