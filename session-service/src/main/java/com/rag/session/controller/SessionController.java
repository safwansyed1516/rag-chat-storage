package com.rag.session.controller;


import com.rag.common.dto.CreateSessionRequest;
import com.rag.common.dto.SessionDto;
import com.rag.session.service.SessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions")
@RequiredArgsConstructor
public class SessionController {
  private final SessionService service;

  @PostMapping
  public ResponseEntity<SessionDto> create(@Valid @RequestBody CreateSessionRequest req){
    return ResponseEntity.status(201).body(service.create(req.title()));
  }

  @GetMapping
  public Page<SessionDto> list(@RequestParam(defaultValue="0") int page,
                               @RequestParam(defaultValue="20") int size){
    return service.list(page,size);
  }

  @PatchMapping("/{id}")
  public SessionDto rename(@PathVariable UUID id,@RequestBody CreateSessionRequest req){
    return service.rename(id, req.title());
  }

  @PatchMapping("/{id}/favorite")
  public SessionDto fav(@PathVariable UUID id,@RequestParam boolean favorite){
    return service.favorite(id,favorite);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> del(@PathVariable UUID id){
    service.delete(id); return ResponseEntity.noContent().build();
  }
}
