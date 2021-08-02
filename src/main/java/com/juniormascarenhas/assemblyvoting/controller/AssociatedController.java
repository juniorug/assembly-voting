package com.juniormascarenhas.assemblyvoting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.juniormascarenhas.assemblyvoting.entity.Associated;
import com.juniormascarenhas.assemblyvoting.service.AssociatedService;

@RestController
@RequestMapping(path = "/associated", produces = "application/json")
public class AssociatedController {

  @Autowired
  private AssociatedService associatedService;

  @PostMapping(consumes = "application/json", headers = "X-API-VERSION=1")
  public ResponseEntity<Associated> save(@RequestBody Associated associated) {
    String associatedId = associatedService.save(associated);
    return ResponseEntity.created(
        ServletUriComponentsBuilder.fromCurrentRequest().path("/{associatedId}").buildAndExpand(associatedId).toUri())
        .build();
  }

}
