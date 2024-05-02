package com.andersen.filedatatransferagent.config.batch;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

import lombok.NonNull;
import org.springframework.batch.item.file.transform.DefaultFieldSet;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.stereotype.Component;

@Component
public class UserLineTokenizer implements LineTokenizer {

  @Override
  public @NonNull FieldSet tokenize(final String line) {
    if (line == null) {
      return new DefaultFieldSet(new String[INTEGER_ZERO]);
    }

    int startIndex = line.indexOf("\"[{");
    String substringWithoutJSON = line.substring(0, startIndex);
    String jsonPart = line.substring(startIndex);
    String[] parts = substringWithoutJSON.split(",");
    String[] partsWithJson = new String[parts.length + 1];
    System.arraycopy(parts, 0, partsWithJson, 0, parts.length);
    partsWithJson[partsWithJson.length - 1] = jsonPart;

    return new DefaultFieldSet(partsWithJson);
  }
}
