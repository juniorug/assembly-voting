package com.juniormascarenhas.assemblyvoting.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.juniormascarenhas.assemblyvoting.config.ApplicationConfig;
import com.juniormascarenhas.assemblyvoting.entity.Assembly;
import com.juniormascarenhas.assemblyvoting.entity.TopicSession;
import com.juniormascarenhas.assemblyvoting.request.AssemblyRequest;
import com.juniormascarenhas.assemblyvoting.request.GetQueryParam;
import com.juniormascarenhas.assemblyvoting.request.TopicSessionRequest;
import com.juniormascarenhas.assemblyvoting.response.AssemblyResponse;
import com.juniormascarenhas.assemblyvoting.service.AssemblyService;

@RestController
@RequestMapping(path = "assemblies", produces = "application/json")
public class AssemblyController {

  private static final String X_API_VERSION_1 = "X-API-VERSION=1";

  @Autowired
  private AssemblyService assemblyService;

  private final ApplicationConfig applicationConfig = new ApplicationConfig();

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, headers = X_API_VERSION_1)
  public ResponseEntity<List<AssemblyResponse>> getAssemblys(GetQueryParam params) {
    Page<Assembly> assemblys = assemblyService.listAssemblys(params);
    return ResponseEntity.ok()
        .header(ControllerConstants.HEADER_CONTENT_RANGE, String.valueOf(assemblys.getTotalElements()))
        .header(ControllerConstants.HEADER_ACCEPT_RANGE, String.valueOf(applicationConfig.getAcceptRange()))
        .body(assemblys.getContent().stream().map(Assembly::toResponse).collect(Collectors.toList()));
  }

  @GetMapping(path = "/{assemblyId}", produces = MediaType.APPLICATION_JSON_VALUE, headers = X_API_VERSION_1)
  public ResponseEntity<AssemblyResponse> getAssembly(@PathVariable String assemblyId) {
    AssemblyResponse assembly = assemblyService.findById(assemblyId);
    return ResponseEntity.ok(assembly);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, headers = X_API_VERSION_1)
  public ResponseEntity<Void> save(@Valid @RequestBody AssemblyRequest assembly) {
    String assemblyId = assemblyService.createAssembly(assembly);
    return ResponseEntity
        .created(
            ServletUriComponentsBuilder.fromCurrentRequest().path("/{assemblyId}").buildAndExpand(assemblyId).toUri())
        .build();
  }

  @PostMapping(path = "{assemblyId}/topic-sessions", consumes = MediaType.APPLICATION_JSON_VALUE, headers = X_API_VERSION_1)
  public ResponseEntity<TopicSession> createTopicSession(@PathVariable(value = "assemblyId") String assemblyId,
      @Valid @RequestBody TopicSessionRequest topicSession) {
    String topicSessionId = assemblyService.createTopicSession(assemblyId, topicSession);
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{topicSessionId}")
        .buildAndExpand(topicSessionId).toUri()).build();
  }

}
