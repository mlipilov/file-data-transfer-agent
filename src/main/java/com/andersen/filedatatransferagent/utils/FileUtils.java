package com.andersen.filedatatransferagent.utils;

import static java.nio.file.Files.createTempFile;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * This class provides utility methods for file operations.
 */
@Slf4j
@UtilityClass
public class FileUtils {

  /**
   * Creates a temporary file with a randomly generated UUID as the file name.
   *
   * @return the path to the created temporary file
   * @throws RuntimeException if an I/O error occurs while creating the temporary file
   */
  public static Path createTmpFile() {
    try {
      return createTempFile(UUID.randomUUID().toString(), EMPTY);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }

  /**
   * Copies the contents of a MultipartFile to a specified Path.
   *
   * @param multipartFile The MultipartFile to copy from
   * @param transferTo The Path to copy the file to
   * @throws RuntimeException if an I/O error occurs while copying the file
   */
  public static void copy(
      final MultipartFile multipartFile,
      final Path transferTo
  ) {
    try {
      Files.copy(multipartFile.getInputStream(), transferTo, REPLACE_EXISTING);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }

  /**
   * Reads all bytes from the specified file.
   *
   * @param path The file path to read from.
   * @return An array of bytes containing the contents of the file.
   * @throws RuntimeException If an I/O error occurs while reading the file.
   */
  public static byte[] readFileBytes(final Path path) {
    try {
      return Files.readAllBytes(path);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }

  /**
   * Deletes a file at the specified path.
   *
   * @param path The path of the file to be deleted.
   * @throws RuntimeException If an I/O error occurs while deleting the file.
   */
  public static void delete(final Path path) {
    try {
      Files.deleteIfExists(path);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }
}
