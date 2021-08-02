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
import com.juniormascarenhas.assemblyvoting.enumeration.TopicResult;

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
  private long timeToBeOpen;

  @Column(name = "TIME_OPENNED")
  private LocalDateTime timeOpenned;

  @Column(name = "STATUS")
  private String status;

  @JsonIgnore
  @Builder.Default
  @OneToMany(orphanRemoval = true, mappedBy = "topicSession")
  private List<Vote> votes = List.of();

  @Column(name = "RESULT")
  private TopicResult result;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "ASSEMBLY_ID")
  private Assembly assembly;

}
