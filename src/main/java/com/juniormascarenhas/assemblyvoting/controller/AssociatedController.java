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
import com.juniormascarenhas.assemblyvoting.entity.Associated;
import com.juniormascarenhas.assemblyvoting.request.AssociatedRequest;
import com.juniormascarenhas.assemblyvoting.request.GetQueryParam;
import com.juniormascarenhas.assemblyvoting.response.AssociatedResponse;
import com.juniormascarenhas.assemblyvoting.service.AssociatedService;

@RestController
@RequestMapping(path = "/associates", produces = "application/json")
public class AssociatedController {

  private static final String X_API_VERSION_1 = "X-API-VERSION=1";

  private final ApplicationConfig applicationConfig = new ApplicationConfig();

  @Autowired
  private AssociatedService associatedService;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, headers = X_API_VERSION_1)
  public ResponseEntity<Void> save(@RequestBody @Valid AssociatedRequest associated) {
    String associatedId = associatedService.createAssociated(associated);
    return ResponseEntity.created(
        ServletUriComponentsBuilder.fromCurrentRequest().path("/{associatedId}").buildAndExpand(associatedId).toUri())
        .build();
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, headers = X_API_VERSION_1)
  public ResponseEntity<List<AssociatedResponse>> getAssociates(GetQueryParam params) {
    Page<Associated> associateds = associatedService.listAssociates(params);
    return ResponseEntity.ok()
        .header(ControllerConstants.HEADER_CONTENT_RANGE, String.valueOf(associateds.getTotalElements()))
        .header(ControllerConstants.HEADER_ACCEPT_RANGE, String.valueOf(applicationConfig.getAcceptRange()))
        .body(associateds.getContent().stream().map(Associated::toResponse).collect(Collectors.toList()));
  }

  @GetMapping(path = "/{associatedId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AssociatedResponse> getAssociated(@PathVariable String associatedId) {
    AssociatedResponse associated = associatedService.findById(associatedId);
    return ResponseEntity.ok(associated);
  }

}
