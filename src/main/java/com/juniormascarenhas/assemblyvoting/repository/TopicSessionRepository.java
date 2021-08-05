package com.juniormascarenhas.assemblyvoting.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.juniormascarenhas.assemblyvoting.entity.Assembly;
import com.juniormascarenhas.assemblyvoting.entity.TopicSession;
import com.juniormascarenhas.assemblyvoting.enumeration.TopicResult;
import com.juniormascarenhas.assemblyvoting.request.GetQueryParam;

public interface TopicSessionRepository extends JpaRepository<TopicSession, String> {

  @Override
  Optional<TopicSession> findById(String id);

  Optional<TopicSession> findByNameAndAssembly(String name, Assembly assembly);
  
  @Query("select ts.result from TopicSession ts where ts.id =:id") 
  Optional<TopicResult> getResult(String id);
  
  List<TopicSession> findByName(String name);
  
  @Query("select ts from TopicSession ts where " 
      + "(:#{#filter.keywords} is null or ("
      + "ts.id like %:#{#filter.keywords}% or " 
      + "ts.name like %:#{#filter.keywords}% or "
      + "ts.statis like %:#{#filter.keywords}% or " 
      + "ts.result like %:#{#filter.keywords}% or "
      + "ts.description like %:#{#filter.keywords}%))")
  Page<TopicSession> findAllByKeywords(@Param("filter") GetQueryParam params, Pageable pageable);

}