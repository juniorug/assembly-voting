package com.juniormascarenhas.assemblyvoting.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juniormascarenhas.assemblyvoting.entity.Associated;
import com.juniormascarenhas.assemblyvoting.exception.ResourceNotFoundException;
import com.juniormascarenhas.assemblyvoting.repository.AssociatedRepository;

@Service
public class AssociatedService {

  @Autowired
  private AssociatedRepository associatedRepository;

  public String save(Associated associated) {
    return associatedRepository.save(associated).getId();
  }

  public List<Associated> getAll() {
    return associatedRepository.findAll();
  }

  public Associated findById(String id) {
    Optional<Associated> associatedOptional = associatedRepository.findById(id);
    if (associatedOptional.isPresent()) {
      return associatedOptional.get();
    } else {
      throw new ResourceNotFoundException();
    }
  }

  public List<Associated> findByName(String name) {
    return associatedRepository.findByName(name);
  }

  public Associated update(String id, Associated newAssociated) {
    Optional<Associated> associated = associatedRepository.findById(id);
    if (associated.isPresent()) {
      associated.map((Function<Associated, Associated>) item -> {
        item.setName(newAssociated.getName());
        item.setCpf(newAssociated.getCpf());
        associatedRepository.save(item);
        return item;
      });
      return associated.get();
    }
    return null;
  }

  public void deleteById(String id) {
    associatedRepository.deleteById(id);
  }

  public void deleteAll() {
    associatedRepository.deleteAll();
  }

}
