package com.juniormascarenhas.assemblyvoting.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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
import com.juniormascarenhas.assemblyvoting.enumeration.TopicResult;
import com.juniormascarenhas.assemblyvoting.enumeration.VoteValue;
import com.juniormascarenhas.assemblyvoting.exception.EntityAlreadyExistsException;
import com.juniormascarenhas.assemblyvoting.exception.ResourceNotFoundException;
import com.juniormascarenhas.assemblyvoting.repository.AssociatedRepository;
import com.juniormascarenhas.assemblyvoting.repository.TopicSessionRepository;
import com.juniormascarenhas.assemblyvoting.repository.VoteRepository;
import com.juniormascarenhas.assemblyvoting.request.GetQueryParam;
import com.juniormascarenhas.assemblyvoting.request.TopicSessionPatchRequest;
import com.juniormascarenhas.assemblyvoting.request.VoteRequest;
import com.juniormascarenhas.assemblyvoting.response.TopicSessionResponse;
import com.juniormascarenhas.assemblyvoting.validator.OpenSessionValidation;

@Service
public class TopicSessionService {

  private static final String TOPIC_SESSION_AND_ASSOCIATED = "topicSession and associated";

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

  @Transactional
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

  /**
   * This method is responsible for open the session for voting First it verifies
   * if the requested Topic Session exists Then perform the validator to verify:
   * if the requested session status is 'OPENED' if the current status is neither
   * 'OPENED' nor 'CLOSED' then updates the current status to 'OPENED' and call
   * runAsyncSheduleToCloseSession to close the session based on TimeToBeOpen
   * field.
   *
   * @param topicSessionId           the Topic Session identifier
   * @param topicSessionPatchRequest the session status to be set
   * @return the updated Topic session
   */
  @Transactional
  public TopicSession openSession(String topicSessionId, TopicSessionPatchRequest topicSessionPatchRequest) {
    Optional<TopicSession> topicSessionOpt = topicSessionRepository.findById(topicSessionId);
    if (topicSessionOpt.isPresent()) {
      TopicSession topicSession = topicSessionOpt.get();
      OpenSessionValidation.validate(topicSession.getStatus(), topicSessionPatchRequest.getStatus());
      topicSession.setDateTimeOpenned(LocalDateTime.now());
      topicSession.setStatus(SessionStatus.OPENED);
      topicSessionRepository.save(topicSession);
      runAsyncSheduleToCloseSession(topicSession);
      return topicSession;
    } else {
      throw new ResourceNotFoundException();
    }
  }

  @Transactional
  public void deleteById(String id) {
    topicSessionRepository.findById(id).ifPresent(topicSession -> {
      topicSessionRepository.delete(topicSession);
    });
  }

  public void deleteAll() {
    topicSessionRepository.deleteAll();
  }

  @Transactional
  public String vote(String topicSessionId, String associatedId, VoteRequest voteRequest) {
    Optional<TopicSession> topicSession = topicSessionRepository.findById(topicSessionId);
    Optional<Associated> associated = associatedRepository.findById(associatedId);
    if (topicSession.isPresent() && associated.isPresent()) {

      voteRepository.findByTopicSessionAndAssociated(topicSession.get(), associated.get()).ifPresent(e -> {
        throw new EntityAlreadyExistsException(TOPIC_SESSION_AND_ASSOCIATED);
      });

      Vote vote = voteRequest.toEntity(associated.get(), topicSession.get());
      return voteRepository.save(vote).getId();
    } else {
      throw new ResourceNotFoundException();
    }
  }

  /**
   * This method is responsible for closing the session based on TimeToBeOpen
   * field in the given topicSession It updates the status to CLOSED after the
   * minutes set in TimeToBeOpen field call the method to update the result field
   * in the given topic session
   * 
   * @param topicSession the Topic Session to be closed.
   * @return void
   */
  private void runAsyncSheduleToCloseSession(TopicSession topicSession) {
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    scheduledExecutorService.schedule(() -> {
      topicSession.setStatus(SessionStatus.CLOSED);
      topicSessionRepository.save(topicSession);
      System.out.println("topicSession with id: " + topicSession.getId() + "Closed.");
      updateTopicSessionResult(topicSession);
      return "Closed!";
    }, topicSession.getTimeToBeOpen(), TimeUnit.MINUTES);
    scheduledExecutorService.shutdown();
  }

  private void updateTopicSessionResult(TopicSession topicSession) {
    Map<String, Long> votesMap = topicSession.getVotes().stream().map(Vote::getValue)
        .collect(Collectors.groupingBy(VoteValue::toString, Collectors.counting()));

    Long yesCount = (null == votesMap.get(VoteValue.YES.name())) ? 0L : votesMap.get(VoteValue.YES.name());
    Long noCount = (null == votesMap.get(VoteValue.NO.name())) ? 0L : votesMap.get(VoteValue.NO.name());
    if (yesCount > noCount) {
      topicSession.setResult(TopicResult.APPROVED);
      System.out.println("topicSession with id: " + topicSession.getId() + "APPROVED.");
    } else if (yesCount < noCount) {
      topicSession.setResult(TopicResult.REJECTED);
      System.out.println("topicSession with id: " + topicSession.getId() + "REJECTED.");
    } else {
      topicSession.setResult(TopicResult.DRAW);
      System.out.println("topicSession with id: " + topicSession.getId() + "DRAW.");
    }
    topicSessionRepository.save(topicSession);
  }
}
