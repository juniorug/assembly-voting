package com.juniormascarenhas.assemblyvoting.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.client.RestTemplate;

import com.juniormascarenhas.assemblyvoting.entity.Associated;
import com.juniormascarenhas.assemblyvoting.entity.TopicSession;
import com.juniormascarenhas.assemblyvoting.entity.Vote;
import com.juniormascarenhas.assemblyvoting.enumeration.AssociatedVoteStatus;
import com.juniormascarenhas.assemblyvoting.enumeration.SessionStatus;
import com.juniormascarenhas.assemblyvoting.exception.ChangeSessionStatusException;
import com.juniormascarenhas.assemblyvoting.exception.InvalidOpenSessionException;
import com.juniormascarenhas.assemblyvoting.exception.MessageError;
import com.juniormascarenhas.assemblyvoting.exception.Messages;
import com.juniormascarenhas.assemblyvoting.exception.MultiUnprocessableEntityException;
import com.juniormascarenhas.assemblyvoting.exception.PreconditionFailedException;
import com.juniormascarenhas.assemblyvoting.model.UserInfo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TopicSessionValidation {

  /**
   * This method is responsible for validate the status change for a Topic Session
   * This is primarily used when opening a session
   * 
   * @param currentState the current state.
   * @param newState     the state desired
   * @throws PreconditionFailedException  if new state is not OPENED
   * @throws ChangeSessionStatusException if current state is OPENED or CLOSED
   * @return void
   */
  public static void validateStatusChange(SessionStatus currentState, SessionStatus newState) {

    if (newState != SessionStatus.OPENED) {
      throw new PreconditionFailedException(new MessageError(Messages.STATUS_RESQUESTED_MUST_BE_OPENED));
    }
    if (currentState == SessionStatus.OPENED) {
      throw new ChangeSessionStatusException(Messages.SESSION_ALREADY_OPENED);
    } else if (currentState == SessionStatus.CLOSED) {
      throw new ChangeSessionStatusException(Messages.SESSION_ALREADY_CLOSED);
    }

  }

  /**
   * This method is responsible for validate the Vote It verifies if the session
   * status is opened then verify if the associated is able to vote and verify if
   * the associated already voted
   * 
   * @param topicSession the topicSession.
   * @param vote         the vote
   * @throws MultiUnprocessableEntityException if session is not OPENED
   * @throws MultiUnprocessableEntityException if associated is unable to vote
   * @throws MultiUnprocessableEntityException if associated already voted
   * @return void
   */
  public static void validateVote(TopicSession topicSession, Vote vote) {

    List<MessageError> errors = new ArrayList<>();
    if (topicSession.getStatus() != SessionStatus.OPENED) {
      errors.add(new MessageError(Messages.SESSION_NOT_OPENED));
    }
    Associated associated = vote.getAssociated();
    if (!ableToVote(associated)) {
      errors.add(new MessageError(Messages.ASSOCIATED_UNABLE_TO_VOTE));
    }
    int associatedCount = topicSession.getVotes().stream().filter(v -> v.getAssociated().equals(associated))
        .collect(Collectors.toList()).size();

    if (associatedCount > 0) {
      errors.add(new MessageError(Messages.ASSOCIATED_ALREADY_VOTED));
    }

    if (!errors.isEmpty()) {
      throw new InvalidOpenSessionException(errors);
    }

  }

  private static boolean ableToVote(Associated associated) {
    final String url = "https://user-info.herokuapp.com/users/".concat(associated.getCpf());

    RestTemplate restTemplate = new RestTemplate();
    UserInfo result = restTemplate.getForObject(url, UserInfo.class);
    if (result == null) {
      result = new UserInfo(AssociatedVoteStatus.UNABLE_TO_VOTE);
    }
    return AssociatedVoteStatus.ABLE_TO_VOTE.equals(result.getStatus());
  }

}
