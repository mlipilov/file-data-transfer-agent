package com.andersen.filedatatransferagent.constants;

import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserCsvConstants {

  public static final String USERNAME = "username";
  public static final String FIRST_NAME = "firstName";
  public static final String LAST_NAME = "lastName";
  public static final String AGE = "age";
  public static final String STREET = "street";
  public static final String CITY = "city";
  public static final String STATE = "state";
  public static final String ZIP = "zip";
  public static final String PHONE_NUMBER = "phoneNumber";
  public static final String EMAIL = "email";
  public static final String WORKSPACES = "workspaces";

  public static final List<String> HEADERS = List.of(
      USERNAME,
      FIRST_NAME,
      LAST_NAME,
      AGE,
      STREET,
      CITY,
      STATE,
      ZIP,
      PHONE_NUMBER,
      EMAIL,
      WORKSPACES);
}
