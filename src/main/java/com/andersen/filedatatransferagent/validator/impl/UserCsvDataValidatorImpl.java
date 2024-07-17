package com.andersen.filedatatransferagent.validator.impl;

import static com.andersen.filedatatransferagent.constants.UserCsvConstants.AGE;
import static com.andersen.filedatatransferagent.constants.UserCsvConstants.EMAIL;
import static com.andersen.filedatatransferagent.constants.UserCsvConstants.HEADERS;
import static com.andersen.filedatatransferagent.constants.UserCsvConstants.USERNAME;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.csv.CSVFormat.DEFAULT;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

import com.andersen.filedatatransferagent.exception.custom.BadRequestException;
import com.andersen.filedatatransferagent.validator.UserCsvDataValidator;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserCsvDataValidatorImpl implements UserCsvDataValidator {

  private static final int MAX_AGE = 120;
  private static final String EMAIL_SYMBOL = "@";
  private static final char DELIMITER = ',';

  @Override
  public void validate(final Path userCsvData) {
    Reader reader = null;
    try {
      reader = new FileReader(userCsvData.toFile());
      final CSVFormat csvFormat = DEFAULT.builder()
          .setDelimiter(DELIMITER)
          .setHeader()
          .setSkipHeaderRecord(true)
          .build();
      final CSVParser csvParser = new CSVParser(reader, csvFormat);

      final List<String> headers = csvParser.getHeaderNames();
      for (final String header : headers) {
        if (!HEADERS.contains(header)) {
          throw new BadRequestException("Invalid header: " + header);
        }
      }

      for (final CSVRecord record : csvParser) {
        final String username = record.get(USERNAME);
        final String age = record.get(AGE);
        final String email = record.get(EMAIL);

        if (isNull(username) || username.isEmpty()) {
          throw new BadRequestException("Invalid Name: " + username);
        }
        if (!isValidAge(age)) {
          throw new BadRequestException("Invalid Age: " + age);
        }
        if (!isValidEmail(email)) {
          throw new BadRequestException("Invalid Email: " + email);
        }
      }

      log.info("CSV file is valid");
    } catch (Throwable ex) {
      log.warn("Unexpected error occurred while validating CSV file", ex);
      throw new RuntimeException(ex);
    } finally {
      closeReader(reader);
    }
  }

  private void closeReader(final Reader reader) {
    try {
      if (nonNull(reader)) {
        reader.close();
      }
    } catch (IOException e) {
      log.error("Unable to close reader", e);
      throw new RuntimeException(e);
    }
  }

  private boolean isValidAge(final String age) {
    try {
      int ageInt = Integer.parseInt(age);
      return ageInt > INTEGER_ZERO && ageInt < MAX_AGE;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private boolean isValidEmail(final String email) {
    return nonNull(email) && email.contains(EMAIL_SYMBOL);
  }
}
