package com.juniormascarenhas.assemblyvoting.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.juniormascarenhas.assemblyvoting.entity.Assembly;
import com.juniormascarenhas.assemblyvoting.request.GetQueryParam;

public interface AssemblyRepository extends JpaRepository<Assembly, String> {

  @Override
  Optional<Assembly> findById(String id);

  Optional<Assembly> findByRealizationDate(LocalDateTime realizationDate);

  @Query("select a from Assembly a where " 
      + "(:#{#filter.keywords} is null or ("
      + "a.id like %:#{#filter.keywords}% or " 
      + "a.realizationDate like %:#{#filter.keywords}% or "
      + "a.description like %:#{#filter.keywords}%))")
  Page<Assembly> findAllByKeywords(@Param("filter") GetQueryParam params, Pageable pageable);

}
