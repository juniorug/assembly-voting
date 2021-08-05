package com.juniormascarenhas.assemblyvoting.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.juniormascarenhas.assemblyvoting.entity.Vote;
import com.juniormascarenhas.assemblyvoting.request.GetQueryParam;

public interface VoteRepository extends JpaRepository<Vote, String> {

  @Override
  Optional<Vote> findById(String id);

  @Query("select v from Vote v where " 
      + "(:#{#filter.keywords} is null or (" 
      + "v.id like %:#{#filter.keywords}% or "
      + "v.topicSession like %:#{#filter.keywords}% or " 
      + "v.value like %:#{#filter.keywords}%))")
  Page<Vote> findAllByKeywords(@Param("filter") GetQueryParam params, Pageable pageable);

}