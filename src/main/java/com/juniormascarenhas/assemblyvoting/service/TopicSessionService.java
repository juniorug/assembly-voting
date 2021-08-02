package com.juniormascarenhas.assemblyvoting.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juniormascarenhas.assemblyvoting.entity.Associated;
import com.juniormascarenhas.assemblyvoting.entity.TopicSession;
import com.juniormascarenhas.assemblyvoting.entity.Vote;
import com.juniormascarenhas.assemblyvoting.enumeration.SessionStatus;
import com.juniormascarenhas.assemblyvoting.exception.ResourceNotFoundException;
import com.juniormascarenhas.assemblyvoting.repository.AssociatedRepository;
import com.juniormascarenhas.assemblyvoting.repository.TopicSessionRepository;
import com.juniormascarenhas.assemblyvoting.repository.VoteRepository;

@Service
public class TopicSessionService {
  @Autowired
  private TopicSessionRepository topicSessionRepository;

  @Autowired
  private VoteRepository voteRepository;

  @Autowired
  private AssociatedRepository associatedRepository;

  public String save(TopicSession topicSession) {
    topicSession.setStatus(SessionStatus.CREATED.name());
    topicSession.setTimeOpenned(LocalDateTime.now());
    return topicSessionRepository.save(topicSession).getId();
  }

  public List<TopicSession> getAll() {
    return topicSessionRepository.findAll();
  }

  public TopicSession findById(String id) {
    Optional<TopicSession> topicSessionOptional = topicSessionRepository.findById(id);
    if (topicSessionOptional.isPresent()) {
      return topicSessionOptional.get();
    } else {
      throw new ResourceNotFoundException();
    }
  }

  public List<TopicSession> findByName(String name) {
    return topicSessionRepository.findByName(name);
  }

  public TopicSession update(String id, TopicSession newTopicSession) {
    Optional<TopicSession> topicSession = topicSessionRepository.findById(id);
    if (topicSession.isPresent()) {
      topicSession.map((Function<TopicSession, TopicSession>) item -> {
        item.setName(newTopicSession.getName());
        item.setDescription(newTopicSession.getDescription());
        item.setTimeToBeOpen(newTopicSession.getTimeToBeOpen());
        item.setTimeOpenned(newTopicSession.getTimeOpenned());
        item.setStatus(newTopicSession.getStatus());
        item.setVotes(newTopicSession.getVotes());
        item.setResult(newTopicSession.getResult());
        topicSessionRepository.save(item);
        return item;
      });
      return topicSession.get();
    } else {
      throw new ResourceNotFoundException();
    }
  }

  public TopicSession openSession(String id) {
    Optional<TopicSession> topicSession = topicSessionRepository.findById(id);
    if (topicSession.isPresent()) {
      topicSession.get().setStatus(SessionStatus.OPENED.name());
      topicSessionRepository.save(topicSession.get());
      return topicSession.get();
    } else {
      throw new ResourceNotFoundException();
    }
  }

  public void deleteById(String id) {
    topicSessionRepository.deleteById(id);
  }

  public void deleteAll() {
    topicSessionRepository.deleteAll();
  }

  public String vote(String topicSessionId, String associatedId, Vote vote) {
    Optional<TopicSession> topicSession = topicSessionRepository.findById(topicSessionId);
    Optional<Associated> associated = associatedRepository.findById(associatedId);
    if (topicSession.isPresent() && associated.isPresent()) {
      vote.setTopicSession(topicSession.get());
      vote.setAssociated(associated.get());
      return voteRepository.save(vote).getId();
    } else {
      throw new ResourceNotFoundException();
    }
  }

}
