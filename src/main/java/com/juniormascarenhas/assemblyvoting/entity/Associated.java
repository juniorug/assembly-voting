package com.juniormascarenhas.assemblyvoting.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.juniormascarenhas.assemblyvoting.response.AssociatedResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_ASSOCIATED")
public class Associated implements Serializable {

  private static final long serialVersionUID = 5505389550366494105L;

  @Id
  @Column(name = "ASSOCIATED_ID")
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private String id;

  @Column(name = "NAME", length = 137)
  private String name;

  @Column(name = "CPF", length = 11)
  private String cpf;

  @Column(name = "CREATED_AT")
  private LocalDateTime createdAt;

  @Column(name = "UPDATED_AT")
  private LocalDateTime updatedAt;
  
  public AssociatedResponse toResponse() {
    return AssociatedResponse.builder()
        .id(id)
        .name(name)
        .cpf(cpf)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();
  }

}