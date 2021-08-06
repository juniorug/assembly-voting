package com.juniormascarenhas.assemblyvoting.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.juniormascarenhas.assemblyvoting.entity.Associated;
import com.juniormascarenhas.assemblyvoting.enumeration.AssociatedVoteStatus;

@Service
public class UserInfoService {

  private final RestTemplate restTemplate;

  public UserInfoService(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  public AssociatedVoteStatus getPostsPlainJSON(Associated associated) {
    String url = "https://user-info.herokuapp.com/users/".concat(associated.getCpf());
    AssociatedVoteStatus associatedVoteStatus = this.restTemplate.getForObject(url, AssociatedVoteStatus.class, 1);
    System.out.println("associatedVoteStatus:" + associatedVoteStatus);
    return associatedVoteStatus;
  }

}
