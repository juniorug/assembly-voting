package com.juniormascarenhas.assemblyvoting.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.juniormascarenhas.assemblyvoting.entity.Assembly;
import com.juniormascarenhas.assemblyvoting.entity.TopicSession;
import com.juniormascarenhas.assemblyvoting.service.AssemblyService;

@RestController
@RequestMapping(path = "/assembly", produces = "application/json")
public class AssemblyController {

  @Autowired
  private AssemblyService assemblyService;

  @PostMapping(consumes = "application/json", headers = "X-API-VERSION=1")
  public ResponseEntity<Assembly> save(@RequestBody Assembly assembly) {
    String assemblyId = assemblyService.save(assembly);
    return ResponseEntity
        .created(
            ServletUriComponentsBuilder.fromCurrentRequest().path("/{assemblyId}").buildAndExpand(assemblyId).toUri())
        .build();
  }

  @PostMapping("{assemblyId}/topic-session")
  public ResponseEntity<TopicSession> createTopicSession(@PathVariable(value = "assemblyId") String assemblyId,
      @Valid @RequestBody TopicSession topicSession) {
    String topicSessionId = assemblyService.createTopicSession(assemblyId, topicSession);
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{assemblyId}/topic-session/")
        .buildAndExpand(topicSessionId).toUri()).build();
  }

  @GetMapping(headers = "X-API-VERSION=1")
  public String hello() {
    return "It is running!!";
  }

  @GetMapping(headers = "X-API-VERSION=2")
  public String helloV2() {
    return "V2222222222222222222222222222222";
  }

}
