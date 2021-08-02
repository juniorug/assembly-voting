package com.juniormascarenhas.assemblyvoting.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juniormascarenhas.assemblyvoting.entity.Associated;

public interface AssociatedRepository extends JpaRepository<Associated, String> {

  @Override
  Optional<Associated> findById(String id);

  List<Associated> findByName(String name);

}