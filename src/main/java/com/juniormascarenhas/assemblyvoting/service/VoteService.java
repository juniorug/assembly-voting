package com.juniormascarenhas.assemblyvoting.service;

import java.util.Optional;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.juniormascarenhas.assemblyvoting.entity.Vote;
import com.juniormascarenhas.assemblyvoting.exception.ResourceNotFoundException;
import com.juniormascarenhas.assemblyvoting.repository.VoteRepository;
import com.juniormascarenhas.assemblyvoting.request.GetQueryParam;
import com.juniormascarenhas.assemblyvoting.response.VoteResponse;

@Service
public class VoteService {

  @Autowired
  private VoteRepository voteRepository;

  public Page<Vote> listVotes(GetQueryParam params) {
    Pageable pageable = PageRequest.of(params.getOffset(), params.getLimit(), params.getSort().getSortBy());
    Page<Vote> votes;
    if (StringUtils.isNotBlank(params.getKeywords())) {
      votes = voteRepository.findAllByKeywords(params, pageable);
    } else {
      votes = voteRepository.findAll(pageable);
    }
    return votes;
  }

  public VoteResponse findById(String voteId) {
    return voteRepository.findById(voteId).map(Vote::toResponse).orElseThrow(ResourceNotFoundException::new);
  }

  public Vote update(String id, Vote newVote) {
    Optional<Vote> vote = voteRepository.findById(id);
    if (vote.isPresent()) {
      vote.map((Function<Vote, Vote>) item -> {
        item.setValue(newVote.getValue());
        voteRepository.save(item);
        return item;
      });
      return vote.get();
    }
    return null;
  }

  public void deleteById(String id) {
    voteRepository.deleteById(id);
  }

  public void deleteAll() {
    voteRepository.deleteAll();
  }

}
