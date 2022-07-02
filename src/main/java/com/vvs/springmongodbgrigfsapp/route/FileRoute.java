package com.vvs.springmongodbgrigfsapp.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class FileRoute {
  
  @Bean
  public RouterFunction<ServerResponse> fileRouterFunction(FileHandler fileHandler) {
    return RouterFunctions.route()
      .nest(RequestPredicates.path("/api/files/"), builder -> builder
        .POST("upload", fileHandler::upload)
        .GET("{id}", fileHandler::download)
        .GET("", fileHandler::listFiles)
        .DELETE("{id}", fileHandler::delete))
      .build();
  }
}
