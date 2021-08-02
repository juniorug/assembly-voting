package com.juniormascarenhas.assemblyvoting.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juniormascarenhas.assemblyvoting.entity.Vote;
import com.juniormascarenhas.assemblyvoting.exception.ResourceNotFoundException;
import com.juniormascarenhas.assemblyvoting.repository.VoteRepository;

@Service
public class VoteService {

  @Autowired
  private VoteRepository voteRepository;

  public String save(Vote vote) {
    return voteRepository.save(vote).getId();
  }

  public List<Vote> getAll() {
    return voteRepository.findAll();
  }

  public Vote findById(String id) {
    Optional<Vote> voteOptional = voteRepository.findById(id);
    if (voteOptional.isPresent()) {
      return voteOptional.get();
    } else {
      throw new ResourceNotFoundException();
    }
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
