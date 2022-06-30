package com.vvs.springmongodbgrigfsapp.route;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vvs.springmongodbgrigfsapp.service.FileServiceImpl;

import reactor.core.publisher.Mono;

@Component
public class FileHandler {
  
  @Autowired
  private FileServiceImpl fileService;

  public Mono<ServerResponse> upload(ServerRequest request) {
    return request.body(BodyExtractors.toMultipartData())
      .map(fileService::addFile)
      .flatMap(id -> ServerResponse
        .ok()
        .body(id, Map.class));
  }

  public Mono<ServerResponse> download(ServerRequest request) {
    return fileService.downloadFile(request.pathVariable("id"))
      .flatMap(file -> ServerResponse
        .ok()
        .contentType(MediaType.IMAGE_JPEG)
        .body(file, DataBuffer.class));
  }
}
