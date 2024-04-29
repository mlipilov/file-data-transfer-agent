package com.andersen.filedatatransferagent.config;

import static com.andersen.filedatatransferagent.constants.UserCsvConstants.AGE;
import static com.andersen.filedatatransferagent.constants.UserCsvConstants.CITY;
import static com.andersen.filedatatransferagent.constants.UserCsvConstants.EMAIL;
import static com.andersen.filedatatransferagent.constants.UserCsvConstants.FIRST_NAME;
import static com.andersen.filedatatransferagent.constants.UserCsvConstants.LAST_NAME;
import static com.andersen.filedatatransferagent.constants.UserCsvConstants.PHONE_NUMBER;
import static com.andersen.filedatatransferagent.constants.UserCsvConstants.STATE;
import static com.andersen.filedatatransferagent.constants.UserCsvConstants.STREET;
import static com.andersen.filedatatransferagent.constants.UserCsvConstants.USERNAME;
import static com.andersen.filedatatransferagent.constants.UserCsvConstants.WORKSPACES;
import static com.andersen.filedatatransferagent.constants.UserCsvConstants.ZIP;

import com.andersen.filedatatransferagent.model.user.User;
import com.andersen.filedatatransferagent.model.user.UserAddress;
import com.andersen.filedatatransferagent.model.user.UserContactDetails;
import com.andersen.filedatatransferagent.model.user.UserIdentifier;
import com.andersen.filedatatransferagent.model.workspace.Workspace;
import com.andersen.filedatatransferagent.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserFieldSetMapper implements FieldSetMapper<User> {

  @Override
  public @NonNull User mapFieldSet(final @NonNull FieldSet fieldSet) {
    log.info("Started to map user field set...");
    final UserIdentifier identifier = getIdentifier(fieldSet);
    final UserAddress address = getAddress(fieldSet);
    final UserContactDetails contactDetails = getContactDetails(fieldSet);
    final List<Workspace> workspaces = getWorkspaces(fieldSet);

    return new User(identifier, address, contactDetails, workspaces);
  }

  private UserIdentifier getIdentifier(final FieldSet fieldSet) {
    return new UserIdentifier(
        fieldSet.readString(USERNAME),
        fieldSet.readString(FIRST_NAME),
        fieldSet.readString(LAST_NAME),
        fieldSet.readInt(AGE)
    );
  }

  private UserAddress getAddress(final FieldSet fieldSet) {
    return new UserAddress(
        fieldSet.readString(STREET),
        fieldSet.readString(CITY),
        fieldSet.readString(STATE),
        fieldSet.readString(ZIP)
    );
  }

  private UserContactDetails getContactDetails(final FieldSet fieldSet) {
    return new UserContactDetails(
        fieldSet.readString(PHONE_NUMBER),
        fieldSet.readString(EMAIL)
    );
  }

  private List<Workspace> getWorkspaces(final FieldSet fieldSet) {
    final String workspacesJsonArray = fieldSet.readString(WORKSPACES);
    return JsonUtils.deserialize(new TypeReference<>() {}, workspacesJsonArray);
  }
}
