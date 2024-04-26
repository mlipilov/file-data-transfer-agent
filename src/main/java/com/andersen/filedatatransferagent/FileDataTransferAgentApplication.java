package com.andersen.filedatatransferagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class FileDataTransferAgentApplication {

  public static void main(String[] args) {
    SpringApplication.run(FileDataTransferAgentApplication.class, args);
  }
}
