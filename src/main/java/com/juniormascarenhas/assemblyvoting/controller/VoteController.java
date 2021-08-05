package com.juniormascarenhas.assemblyvoting.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juniormascarenhas.assemblyvoting.config.ApplicationConfig;
import com.juniormascarenhas.assemblyvoting.entity.Vote;
import com.juniormascarenhas.assemblyvoting.request.GetQueryParam;
import com.juniormascarenhas.assemblyvoting.response.VoteResponse;
import com.juniormascarenhas.assemblyvoting.service.VoteService;

@RestController
@RequestMapping(path = "votes", produces = "application/json")
public class VoteController {

  private static final String X_API_VERSION_1 = "X-API-VERSION=1";

  @Autowired
  private VoteService voteService;

  private final ApplicationConfig applicationConfig = new ApplicationConfig();

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, headers = X_API_VERSION_1)
  public ResponseEntity<List<VoteResponse>> getVotes(GetQueryParam params) {
    Page<Vote> votes = voteService.listVotes(params);
    return ResponseEntity.ok()
        .header(ControllerConstants.HEADER_CONTENT_RANGE, String.valueOf(votes.getTotalElements()))
        .header(ControllerConstants.HEADER_ACCEPT_RANGE, String.valueOf(applicationConfig.getAcceptRange()))
        .body(votes.getContent().stream().map(Vote::toResponse).collect(Collectors.toList()));
  }

  @GetMapping(path = "/{voteId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<VoteResponse> getVote(@PathVariable String voteId) {
    VoteResponse vote = voteService.findById(voteId);
    return ResponseEntity.ok(vote);
  }

}
