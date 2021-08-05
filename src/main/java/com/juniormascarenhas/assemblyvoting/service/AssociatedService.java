package com.juniormascarenhas.assemblyvoting.service;

import java.util.Optional;
import java.util.function.Function;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.juniormascarenhas.assemblyvoting.entity.Associated;
import com.juniormascarenhas.assemblyvoting.exception.EntityAlreadyExistsException;
import com.juniormascarenhas.assemblyvoting.exception.ResourceNotFoundException;
import com.juniormascarenhas.assemblyvoting.repository.AssociatedRepository;
import com.juniormascarenhas.assemblyvoting.request.AssociatedRequest;
import com.juniormascarenhas.assemblyvoting.request.GetQueryParam;
import com.juniormascarenhas.assemblyvoting.response.AssociatedResponse;

@Service
public class AssociatedService {

  private static final String CPF = "cpf";

  @Autowired
  private AssociatedRepository associatedRepository;

  @Transactional
  public String createAssociated(AssociatedRequest associatedRequest) {
    associatedRepository.findByCpf(associatedRequest.getCpf()).ifPresent(e -> {
      throw new EntityAlreadyExistsException(CPF);
    });
    return associatedRepository.save(associatedRequest.toEntity()).getId();
  }

  public Page<Associated> listAssociates(GetQueryParam params) {
    Pageable pageable = PageRequest.of(params.getOffset(), params.getLimit(), params.getSort().getSortBy());
    Page<Associated> associates;
    if (StringUtils.isNotBlank(params.getKeywords())) {
      associates = associatedRepository.findAllByKeywords(params, pageable);
    } else {
      associates = associatedRepository.findAll(pageable);
    }
    return associates;
  }

  public AssociatedResponse findById(String associatedId) {
    return associatedRepository.findById(associatedId).map(Associated::toResponse)
        .orElseThrow(ResourceNotFoundException::new);
  }

  public Page<Associated> findByName(String name) {
    return new PageImpl<>(associatedRepository.findByName(name));
  }

  @Transactional
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

  @Transactional
  public void deleteById(String id) {
    associatedRepository.findById(id).ifPresent(associated -> {
      associatedRepository.delete(associated);
    });
  }

  public void deleteAll() {
    associatedRepository.deleteAll();
  }

}
