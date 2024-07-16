package com.andersen.filedatatransferagent.validator;

import java.nio.file.Path;

/**
 * The UserCsvDataValidator interface provides a way to validate user data from a temporary file.
 */
public interface UserCsvDataValidator {

  /**
   * Validates the given temporary file.
   *
   * @param userCsvData The temporary file to validate
   */
  void validate(Path userCsvData);
}
