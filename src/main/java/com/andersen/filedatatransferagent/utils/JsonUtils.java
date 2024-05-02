package com.andersen.filedatatransferagent.utils;

import com.andersen.filedatatransferagent.model.workspace.Workspace;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@UtilityClass
public class JsonUtils {

  public static <T> List<T> deserialize(
      final TypeReference<List<T>> typeReference,
      final String json
  ) {
    final ObjectMapper om = getObjectMapper();
    final String noLeadingQuotes = json.replaceAll("^\"|\"$", "");
    final String validJson = noLeadingQuotes.replaceAll("\"\"", "\"");

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
