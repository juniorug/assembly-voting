package com.juniormascarenhas.assemblyvoting.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juniormascarenhas.assemblyvoting.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, String> {

  @Override
  Optional<Vote> findById(String id);

}