package com.andersen.filedatatransferagent.constants;

import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserCsvConstants {

  public static final List<String> HEADERS = List.of(
      "username",
      "firstName",
      "lastName",
      "age",
      "street",
      "city",
      "state",
      "zip",
      "phoneNumber",
      "email");

  public static final String FILE_NAME = "users.csv";
}
