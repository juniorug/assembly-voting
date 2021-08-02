package com.juniormascarenhas.assemblyvoting.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juniormascarenhas.assemblyvoting.entity.Assembly;

public interface AssemblyRepository extends JpaRepository<Assembly, String> {

  @Override
  Optional<Assembly> findById(String id);

  List<Assembly> findByRealizationDate(LocalDateTime realizationDate);

}
