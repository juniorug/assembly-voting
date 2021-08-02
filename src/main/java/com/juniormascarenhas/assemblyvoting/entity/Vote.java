package com.juniormascarenhas.assemblyvoting.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.juniormascarenhas.assemblyvoting.enumeration.VoteValue;

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
@Table(name = "T_VOTE")
public class Vote implements Serializable {

  private static final long serialVersionUID = -2712397454790361356L;

  @Id
  @Column(name = "VOTE_ID")
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private String id;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "ASSOCIATED_ID")
  private Associated associated;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "TOPIC_SESSION_ID")
  private TopicSession topicSession;

  @Column(name = "VALUE", length = 3)
  private VoteValue value;

}
