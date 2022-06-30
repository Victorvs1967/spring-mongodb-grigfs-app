package com.vvs.springmongodbgrigfsapp.model;

import org.springframework.core.io.buffer.DataBuffer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoadFile {
  
  private String fileName;
  private String fileType;
  private String fileSize;
  private DataBuffer file;

}
