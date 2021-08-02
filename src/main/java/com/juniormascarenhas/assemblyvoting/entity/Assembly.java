package com.juniormascarenhas.assemblyvoting.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.juniormascarenhas.assemblyvoting.response.AssemblyResponse;

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
@Table(name = "T_ASSEMBLY")
public class Assembly implements Serializable {

  private static final long serialVersionUID = 8662339341215044485L;

  @Id
  @Column(name = "ASSEMBLY_ID")
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private String id;

  @Column(name = "DESCRIPTION", length = 1024)
  private String description;

  @Column(name = "REALIZATION_DATE")
  @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
  private LocalDateTime realizationDate;

  @Column(name = "CREATED_AT")
  private LocalDateTime createdAt;

  @Column(name = "UPDATED_AT")
  private LocalDateTime updatedAt;

  @JsonIgnore
  @Builder.Default
  @OneToMany(orphanRemoval = true, mappedBy = "assembly")
  private List<TopicSession> topicSessions = List.of();

  public AssemblyResponse toResponse() {
    return AssemblyResponse.builder().id(id).description(description).realizationDate(realizationDate)
        .createdAt(createdAt).updatedAt(updatedAt).topicSessions(topicSessions).build();
  }

}
