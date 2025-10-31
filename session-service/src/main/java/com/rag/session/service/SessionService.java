package com.rag.session.service;


import com.rag.common.dto.SessionDto;
import com.rag.session.entity.SessionEntity;
import com.rag.session.repository.SessionRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

import java.util.UUID;

@Service @RequiredArgsConstructor
public class SessionService {
  private final SessionRepository repo;


  public SessionDto create(String title){
    var s = new SessionEntity(); s.setTitle(title);
    return toDto(repo.save(s));
  }

  public Page<SessionDto> list(int page, int size){
    return repo.findAll(PageRequest.of(page, size, Sort.by("updatedAt").descending()))
               .map(this::toDto);
  }

  public SessionDto rename(UUID id, String title){
    var s = repo.findById(id).orElseThrow();
    s.setTitle(title); return toDto(repo.save(s));
  }

  public SessionDto favorite(UUID id, boolean fav){
    var s = repo.findById(id).orElseThrow();
    s.setFavorite(fav); return toDto(repo.save(s));
  }

  public void delete(UUID id){ repo.deleteById(id); }

  private SessionDto toDto(SessionEntity e){
    return new SessionDto(e.getId(), e.getTitle(), e.isFavorite(), e.getCreatedAt(), e.getUpdatedAt());
  }
}
