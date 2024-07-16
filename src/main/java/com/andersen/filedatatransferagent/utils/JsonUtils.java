package com.andersen.filedatatransferagent.utils;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * This class provides utility methods for working with JSON data.
 */
@Slf4j
@UtilityClass
public class JsonUtils {

  public static final String UNNECESSARY_QUOTES_REGEXP_1 = "^\"|\"$";
  public static final String UNNECESSARY_QUOTES_REGEXP_2 = "\"\"";
  public static final String SINGLE_QUOT = "\"";

  /**
   * Deserializes a JSON string into a list of objects of the specified type.
   *
   * @param typeReference The type reference specifying the target object type.
   * @param json The JSON string to be deserialized.
   * @param <T> The type of objects in the list to be deserialized.
   * @return The deserialized list of objects.
   * @throws RuntimeException if there is an error during deserialization.
   */
  public static <T> List<T> deserialize(
      final TypeReference<List<T>> typeReference,
      final String json
  ) {
    final ObjectMapper om = getObjectMapper();
    final String noLeadingQuotes = json.replaceAll(UNNECESSARY_QUOTES_REGEXP_1, EMPTY);
    final String validJson = noLeadingQuotes.replaceAll(UNNECESSARY_QUOTES_REGEXP_2, SINGLE_QUOT);

    try {
      return om.readValue(validJson, typeReference);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }

  private static ObjectMapper getObjectMapper() {
    final ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();
    return objectMapper;
  }
}
