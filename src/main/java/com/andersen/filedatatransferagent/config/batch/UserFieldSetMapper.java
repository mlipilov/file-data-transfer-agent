package com.andersen.filedatatransferagent.config.batch;

import static com.andersen.filedatatransferagent.constants.UserCsvConstants.AGE_INDEX;
import static com.andersen.filedatatransferagent.constants.UserCsvConstants.CITY_INDEX;
import static com.andersen.filedatatransferagent.constants.UserCsvConstants.EMAIL_INDEX;
import static com.andersen.filedatatransferagent.constants.UserCsvConstants.FIRST_NAME_INDEX;
import static com.andersen.filedatatransferagent.constants.UserCsvConstants.LAST_NAME_INDEX;
import static com.andersen.filedatatransferagent.constants.UserCsvConstants.PHONE_NUMBER_INDEX;
import static com.andersen.filedatatransferagent.constants.UserCsvConstants.STATE_INDEX;
import static com.andersen.filedatatransferagent.constants.UserCsvConstants.STREET_INDEX;
import static com.andersen.filedatatransferagent.constants.UserCsvConstants.USERNAME_INDEX;
import static com.andersen.filedatatransferagent.constants.UserCsvConstants.WORKSPACES_INDEX;
import static com.andersen.filedatatransferagent.constants.UserCsvConstants.ZIP_INDEX;
import static com.andersen.filedatatransferagent.utils.JsonUtils.deserialize;

import com.andersen.filedatatransferagent.model.user.User;
import com.andersen.filedatatransferagent.model.workspace.Workspace;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

/**
 * The UserFieldSetMapper class is responsible for mapping a FieldSet to a User object.
 */
@Slf4j
@Component
public class UserFieldSetMapper implements FieldSetMapper<User> {

  /**
   * Maps a {@link FieldSet} to a {@link User} object.
   *
   * @param fieldSet the field set to be mapped
   * @return the mapped {@link User} object
   */
  @Override
  public @NonNull User mapFieldSet(final @NonNull FieldSet fieldSet) {
    log.info("Started creating User object from the FieldSet");
    final List<Workspace> workspaces = getWorkspaces(fieldSet);

    return new User(
        fieldSet.readString(USERNAME_INDEX),
        fieldSet.readString(FIRST_NAME_INDEX),
        fieldSet.readString(LAST_NAME_INDEX),
        fieldSet.readInt(AGE_INDEX),
        fieldSet.readString(STREET_INDEX),
        fieldSet.readString(CITY_INDEX),
        fieldSet.readString(STATE_INDEX),
        fieldSet.readString(ZIP_INDEX),
        fieldSet.readString(PHONE_NUMBER_INDEX),
        fieldSet.readString(EMAIL_INDEX),
        workspaces);
  }

  private List<Workspace> getWorkspaces(final FieldSet fieldSet) {
    final String workspacesJsonArray = fieldSet.readString(WORKSPACES_INDEX);
    return deserialize(new TypeReference<>() {}, workspacesJsonArray);
  }
}
