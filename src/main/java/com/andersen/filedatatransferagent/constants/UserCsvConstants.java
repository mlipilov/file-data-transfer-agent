package com.andersen.filedatatransferagent.constants;

import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserCsvConstants {

  public static final Integer USERNAME_INDEX = 0;
  public static final Integer FIRST_NAME_INDEX = 1;
  public static final Integer LAST_NAME_INDEX = 2;
  public static final Integer AGE_INDEX = 3;
  public static final Integer STREET_INDEX = 4;
  public static final Integer CITY_INDEX = 5;
  public static final Integer STATE_INDEX = 6;
  public static final Integer ZIP_INDEX = 7;
  public static final Integer PHONE_NUMBER_INDEX = 8;
  public static final Integer EMAIL_INDEX = 9;
  public static final Integer WORKSPACES_INDEX = 10;

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

  public static final String FILE_NAME = "users.csv";
}
