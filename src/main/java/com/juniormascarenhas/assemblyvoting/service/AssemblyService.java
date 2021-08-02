package com.juniormascarenhas.assemblyvoting.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.juniormascarenhas.assemblyvoting.entity.Assembly;
import com.juniormascarenhas.assemblyvoting.entity.TopicSession;
import com.juniormascarenhas.assemblyvoting.enumeration.SessionStatus;
import com.juniormascarenhas.assemblyvoting.exception.EntityAlreadyExistsException;
import com.juniormascarenhas.assemblyvoting.exception.ResourceNotFoundException;
import com.juniormascarenhas.assemblyvoting.repository.AssemblyRepository;
import com.juniormascarenhas.assemblyvoting.repository.TopicSessionRepository;
import com.juniormascarenhas.assemblyvoting.request.AssemblyRequest;
import com.juniormascarenhas.assemblyvoting.request.GetAssemblysQueryParam;

@Service
public class AssemblyService {

  private static final String REALIZATION_DATE = "realizationDate";

  @Autowired
  private AssemblyRepository assemblyRepository;

  @Autowired
  private TopicSessionRepository topicSessionRepository;

  public String save(Assembly assembly) {
    return assemblyRepository.save(assembly).getId();
  }

  @Transactional
  public String createAssembly(AssemblyRequest assemblyRequest) {
    assemblyRepository.findByRealizationDate(assemblyRequest.getRealizationDate()).ifPresent(e -> {
      throw new EntityAlreadyExistsException(REALIZATION_DATE);
    });
    return assemblyRepository.save(assemblyRequest.toEntity()).getId();
  }

  public Page<Assembly> listAssemblys(GetAssemblysQueryParam params) {
    System.out.println("params: " + params.toString());
    Pageable pageable = PageRequest.of(params.getOffset(), params.getLimit(), params.getSort().getSortBy());
    Page<Assembly> assemblys;
    if (StringUtils.isNotBlank(params.getKeywords())) {
      assemblys = assemblyRepository.findAllByKeywords(params, pageable);
    } else {
      assemblys = assemblyRepository.findAll(pageable);
    }
    return assemblys;
  }

  public Assembly findById(String id) {
    Optional<Assembly> assemblyOptional = assemblyRepository.findById(id);
    if (assemblyOptional.isPresent()) {
      return assemblyOptional.get();
    } else {
      throw new ResourceNotFoundException();
    }
  }

  public Optional<Assembly> findByRealizationDate(LocalDateTime realizationDate) {
    return assemblyRepository.findByRealizationDate(realizationDate);
  }

  public Assembly update(String id, Assembly newAssembly) {
    Optional<Assembly> assembly = assemblyRepository.findById(id);
    if (assembly.isPresent()) {
      assembly.map((Function<Assembly, Assembly>) item -> {
        item.setRealizationDate(newAssembly.getRealizationDate());
        assemblyRepository.save(item);
        return item;
      });
      return assembly.get();
    }
    return null;
  }

  public void deleteById(String id) {
    assemblyRepository.deleteById(id);
  }

  public void deleteAll() {
    assemblyRepository.deleteAll();
  }

  public String createTopicSession(String assemblyId, TopicSession topicSession) {
    return assemblyRepository.findById(assemblyId).map(assembly -> {
      topicSession.setStatus(SessionStatus.CREATED.name());
      topicSession.setTimeOpenned(LocalDateTime.now());
      topicSession.setAssembly(assembly);
      return topicSessionRepository.save(topicSession).getId();
    }).orElseThrow(() -> new ResourceNotFoundException("AssemblyId " + assemblyId + " not found"));
  }
}
