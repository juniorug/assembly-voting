package com.juniormascarenhas.assemblyvoting.service;

import java.util.Optional;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.juniormascarenhas.assemblyvoting.entity.Associated;
import com.juniormascarenhas.assemblyvoting.entity.TopicSession;
import com.juniormascarenhas.assemblyvoting.entity.Vote;
import com.juniormascarenhas.assemblyvoting.enumeration.SessionStatus;
import com.juniormascarenhas.assemblyvoting.exception.ResourceNotFoundException;
import com.juniormascarenhas.assemblyvoting.repository.AssociatedRepository;
import com.juniormascarenhas.assemblyvoting.repository.TopicSessionRepository;
import com.juniormascarenhas.assemblyvoting.repository.VoteRepository;
import com.juniormascarenhas.assemblyvoting.request.GetQueryParam;
import com.juniormascarenhas.assemblyvoting.response.TopicSessionResponse;

@Service
public class TopicSessionService {
  @Autowired
  private TopicSessionRepository topicSessionRepository;

  @Autowired
  private VoteRepository voteRepository;

  @Autowired
  private AssociatedRepository associatedRepository;

  public Page<TopicSession> listTopicSessions(GetQueryParam params) {
    Pageable pageable = PageRequest.of(params.getOffset(), params.getLimit(), params.getSort().getSortBy());
    Page<TopicSession> topicSessions;
    if (StringUtils.isNotBlank(params.getKeywords())) {
      topicSessions = topicSessionRepository.findAllByKeywords(params, pageable);
    } else {
      topicSessions = topicSessionRepository.findAll(pageable);
    }
    return topicSessions;
  }

  public TopicSessionResponse findById(String id) {
    return topicSessionRepository.findById(id).map(TopicSession::toResponse)
        .orElseThrow(ResourceNotFoundException::new);
  }

  public Page<TopicSession> findByName(String name) {
    return new PageImpl<>(topicSessionRepository.findByName(name));
  }

  public TopicSession update(String id, TopicSession newTopicSession) {
    Optional<TopicSession> topicSession = topicSessionRepository.findById(id);
    if (topicSession.isPresent()) {
      topicSession.map((Function<TopicSession, TopicSession>) item -> {
        item.setName(newTopicSession.getName());
        item.setDescription(newTopicSession.getDescription());
        item.setTimeToBeOpen(newTopicSession.getTimeToBeOpen());
        item.setDateTimeOpenned(newTopicSession.getDateTimeOpenned());
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
      topicSession.get().setStatus(SessionStatus.OPENED);
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
