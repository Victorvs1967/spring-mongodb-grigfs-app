package com.vvs.springmongodbgrigfsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Map;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class FileServiceImpl implements FileService {
  
  @Autowired
  private ReactiveGridFsTemplate gridFsTemplate;

  public Mono<Map<String, String>> addFile(MultiValueMap<String, Part> upload) {
    DBObject metadata = new BasicDBObject();
    metadata.put("fileSize", upload.size());
    metadata.put("_contentType", "image/jpeg");

    return Mono.just(upload)
      .map(parts -> parts.toSingleValueMap())
      .map(map -> (FilePart) map.get("file"))
      .flatMap(part -> gridFsTemplate.store(part.content(), part.filename(), metadata))
      .map(id -> Map.of("id", id.toHexString()))
      .map(_id -> _id);
  }

  public Mono<?> downloadFile(String id) {
    return gridFsTemplate.findOne(query(where("_id").is(id)))
        .flatMap(gridFsTemplate::getResource)
        .map(res -> res.getContent());
  }

  public Mono<Void> delete(String id) {
    return gridFsTemplate.delete(query(where("_id").is(id)));
  }

	@Override
	public Flux<Map<String, String>> listFiles() {
		return gridFsTemplate.find(new Query())
      .map(file -> Map.of(file.getObjectId().toHexString(), file.getFilename()));
	}

}
