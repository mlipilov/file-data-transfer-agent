package com.andersen.filedatatransferagent.config.batch;

import com.andersen.filedatatransferagent.model.user.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

/**
 * The UserLineMapper class is responsible for mapping a line of user data to a User object.
 */
@Component
@RequiredArgsConstructor
public class UserLineMapper implements LineMapper<User> {

  private final UserLineTokenizer userLineTokenizer;
  private final UserFieldSetMapper userFieldSetMapper;

  /**
   * Maps a line of user data to a User object.
   *
   * @param line        the line of user data to be mapped
   * @param lineNumber  the line number of the input file
   * @return the mapped User object
   */
  @Override
  public @NonNull User mapLine(@NonNull final String line, final int lineNumber) {
    final FieldSet fieldSet = userLineTokenizer.tokenize(line);
    return userFieldSetMapper.mapFieldSet(fieldSet);
  }
}
