package com.andersen.filedatatransferagent.config.batch;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

import lombok.NonNull;
import org.springframework.batch.item.file.transform.DefaultFieldSet;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.stereotype.Component;

/**
 * The UserLineTokenizer class is responsible for tokenizing a line of user data into fields.
 */
@Component
public class UserLineTokenizer implements LineTokenizer {

  public static final String JSON_INDEX = "\"[{";
  public static final String COMA_REGEX = ",";

  /**
   * Tokenizes a given line into a FieldSet.
   *
   * @param line the line to be tokenized
   * @return the FieldSet containing the tokens
   */
  @Override
  public @NonNull FieldSet tokenize(final String line) {
    if (line == null) {
      return new DefaultFieldSet(new String[INTEGER_ZERO]);
    }

    final int startIndex = line.indexOf(JSON_INDEX);
    final String substringWithoutJSON = line.substring(INTEGER_ZERO, startIndex);
    final String jsonPart = line.substring(startIndex);
    final String[] parts = substringWithoutJSON.split(COMA_REGEX);
    final String[] partsWithJson = new String[parts.length + INTEGER_ONE];
    System.arraycopy(parts, INTEGER_ZERO, partsWithJson, INTEGER_ZERO, parts.length);
    partsWithJson[partsWithJson.length - INTEGER_ONE] = jsonPart;

    return new DefaultFieldSet(partsWithJson);
  }
}
