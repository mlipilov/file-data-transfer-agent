package com.andersen.filedatatransferagent.validator.impl;

import static org.apache.commons.csv.CSVFormat.DEFAULT;

import com.andersen.filedatatransferagent.validator.UserCsvDataValidator;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserCsvDataValidatorImpl implements UserCsvDataValidator {

  @Override
  @SneakyThrows
  public void validate(final Path userCsvData) {
    Reader reader = null;
    try {
      reader = new FileReader(userCsvData.toFile());
      final CSVParser csvParser = new CSVParser(reader, DEFAULT);

      // Validate headers
      final List<String> headers = csvParser.getHeaderNames();
      for (String header : headers) {
        //!CsvSchema.isValidHeader(header)
        if (true) {
          throw new IllegalArgumentException("Invalid header: " + header);
        }
      }

      // Validate records
      for (final CSVRecord record : csvParser) {
        final String name = record.get("Name");
        final String age = record.get("Age");
        final String email = record.get("Email");

        if (name == null || name.isEmpty()) {
          throw new IllegalArgumentException("Invalid Name: " + name);
        }
        //!CsvSchema.isValidAge(age)
        if (true) {
          throw new IllegalArgumentException("Invalid Age: " + age);
        }
        //!CsvSchema.isValidEmail(email)
        if (true) {
          throw new IllegalArgumentException("Invalid Email: " + email);
        }
      }

      log.info("CSV file is valid");
    } catch (Throwable ex) {
      log.warn("CSV file is not valid", ex);
      //TODO add BadRequestException
      throw new RuntimeException(ex);
    } finally {
      if (Objects.nonNull(reader)) {
        reader.close();
      }
    }
  }
}
