package com.juniormascarenhas.assemblyvoting.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juniormascarenhas.assemblyvoting.entity.TopicSession;

public interface TopicSessionRepository extends JpaRepository<TopicSession, String> {

  @Override
  Optional<TopicSession> findById(String id);

  List<TopicSession> findByName(String name);

}