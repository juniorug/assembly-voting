package com.juniormascarenhas.assemblyvoting.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.juniormascarenhas.assemblyvoting.enumeration.SessionStatus;
import com.juniormascarenhas.assemblyvoting.enumeration.TopicResult;
import com.juniormascarenhas.assemblyvoting.response.TopicSessionResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_TOPIC_SESSION")
public class TopicSession implements Serializable {

  private static final long serialVersionUID = 2064522912245968172L;

  @Id
  @Column(name = "TOPIC_SESSION_ID")
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private String id;

  @Column(name = "NAME", length = 137)
  private String name;

  @Column(name = "DESCRIPTION", length = 137)
  private String description;

  @Column(name = "TIME_TO_BE_OPEN")
  private int timeToBeOpen;

  @Column(name = "TIME_OPENNED")
  private LocalDateTime dateTimeOpenned;

  @Column(name = "STATUS")
  private SessionStatus status;

  @Column(name = "RESULT")
  private TopicResult result;

  @Column(name = "CREATED_AT")
  private LocalDateTime createdAt;

  @Column(name = "UPDATED_AT")
  private LocalDateTime updatedAt;

  @JsonIgnore
  @Builder.Default
  @OneToMany(orphanRemoval = true, mappedBy = "topicSession")
  private List<Vote> votes = List.of();

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "ASSEMBLY_ID")
  private Assembly assembly;

  public TopicSessionResponse toResponse() {
    return TopicSessionResponse.builder().id(id).description(description).timeToBeOpen(timeToBeOpen)
        .dateTimeOpenned(dateTimeOpenned).status(status).createdAt(createdAt).updatedAt(updatedAt)
        .assemblyId(assembly.getId()).topicResult(result).votes(votes).build();
  }

}
