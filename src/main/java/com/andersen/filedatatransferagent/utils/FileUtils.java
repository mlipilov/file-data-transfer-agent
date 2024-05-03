package com.andersen.filedatatransferagent.utils;

import static java.nio.file.Files.createTempFile;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@UtilityClass
public class FileUtils {

  public static Path createTmpFile() {
    try {
      return createTempFile(UUID.randomUUID().toString(), EMPTY);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }

  public static void transferResourceData(
      final MultipartFile multipartFile,
      final Path transferTo
  ) {
    try {
      multipartFile.transferTo(transferTo);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }

  public static byte[] readFileBytes(final Path path) {
    try {
      return Files.readAllBytes(path);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }

  public static void delete(final Path path) {
    try {
      Files.deleteIfExists(path);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }
}
