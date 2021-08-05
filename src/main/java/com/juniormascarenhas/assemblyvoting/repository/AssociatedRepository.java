package com.juniormascarenhas.assemblyvoting.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.juniormascarenhas.assemblyvoting.entity.Associated;
import com.juniormascarenhas.assemblyvoting.request.GetQueryParam;

public interface AssociatedRepository extends JpaRepository<Associated, String> {

  @Override
  Optional<Associated> findById(String id);
  
  Optional<Associated> findByCpf(String cpf);

  List<Associated> findByName(String name);
  
  @Query("select a from Associated a where " 
      + "(:#{#filter.keywords} is null or ("
      + "a.id like %:#{#filter.keywords}% or " 
      + "a.name like %:#{#filter.keywords}% or "
      + "a.cpf like %:#{#filter.keywords}%))")
  Page<Associated> findAllByKeywords(@Param("filter") GetQueryParam params, Pageable pageable);

}