package com.juniormascarenhas.assemblyvoting.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.juniormascarenhas.assemblyvoting.entity.TopicSession;
import com.juniormascarenhas.assemblyvoting.entity.Vote;
import com.juniormascarenhas.assemblyvoting.service.TopicSessionService;

@RestController
@RequestMapping(path = "/topic-sessions", produces = "application/json")
public class TopicSessionController {

  @Autowired
  private TopicSessionService topicSessionService;

  @PostMapping(consumes = "application/json", headers = "X-API-VERSION=1")
  public ResponseEntity<TopicSession> save(@RequestBody TopicSession topicSession) {
    String topicSessionId = topicSessionService.save(topicSession);
    return ResponseEntity.created(
        ServletUriComponentsBuilder.fromCurrentRequest().path("/{assemblyId}").buildAndExpand(topicSessionId).toUri())
        .build();
  }

  @PatchMapping(path = "/{id}", headers = "X-API-VERSION=1")
  public ResponseEntity<TopicSession> openSession(@PathVariable("id") String id) {
    TopicSession topicSession = topicSessionService.openSession(id);
    return ResponseEntity.ok(topicSession);
  }

  @PostMapping("{topicSessionId}/associates/{associatedId}/vote")
  public ResponseEntity<Vote> vote(@PathVariable(value = "topicSessionId") String topicSessionId,
      @PathVariable(value = "associatedId") String associatedId, @Valid @RequestBody Vote vote) {
    String voteId = topicSessionService.vote(topicSessionId, associatedId, vote);
    return ResponseEntity
        .created(ServletUriComponentsBuilder.fromCurrentRequest().path("/vote").buildAndExpand(voteId).toUri()).build();
  }

}
