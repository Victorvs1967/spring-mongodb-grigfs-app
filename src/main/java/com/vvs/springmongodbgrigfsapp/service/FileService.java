package com.vvs.springmongodbgrigfsapp.service;

import java.util.Map;

import org.springframework.http.codec.multipart.Part;
import org.springframework.util.MultiValueMap;

import reactor.core.publisher.Mono;

public interface FileService {
  
  public Mono<Map<String, String>> addFile(MultiValueMap<String, Part> upload);
  public Mono<?> downloadFile(String id);
  
}
