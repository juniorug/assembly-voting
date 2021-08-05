package com.juniormascarenhas.assemblyvoting.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.juniormascarenhas.assemblyvoting.config.ApplicationConfig;
import com.juniormascarenhas.assemblyvoting.entity.TopicSession;
import com.juniormascarenhas.assemblyvoting.entity.Vote;
import com.juniormascarenhas.assemblyvoting.request.GetQueryParam;
import com.juniormascarenhas.assemblyvoting.response.TopicSessionResponse;
import com.juniormascarenhas.assemblyvoting.service.TopicSessionService;

@RestController
@RequestMapping(path = "/topic-sessions", produces = "application/json")
public class TopicSessionController {

  private static final String X_API_VERSION_1 = "X-API-VERSION=1";

  private final ApplicationConfig applicationConfig = new ApplicationConfig();

  @Autowired
  private TopicSessionService topicSessionService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, headers = X_API_VERSION_1)
  public ResponseEntity<List<TopicSessionResponse>> getTopicSessions(GetQueryParam params) {
    Page<TopicSession> topicSessions = topicSessionService.listTopicSessions(params);
    return ResponseEntity.ok()
        .header(ControllerConstants.HEADER_CONTENT_RANGE, String.valueOf(topicSessions.getTotalElements()))
        .header(ControllerConstants.HEADER_ACCEPT_RANGE, String.valueOf(applicationConfig.getAcceptRange()))
        .body(topicSessions.getContent().stream().map(TopicSession::toResponse).collect(Collectors.toList()));
  }

  @GetMapping(path = "/{topicSessionId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TopicSessionResponse> getTopicSession(@PathVariable String topicSessionId) {
    TopicSessionResponse topicSession = topicSessionService.findById(topicSessionId);
    return ResponseEntity.ok(topicSession);
  }

  @PatchMapping(path = "/{id}", headers = X_API_VERSION_1)
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
