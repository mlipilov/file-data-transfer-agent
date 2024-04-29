package com.andersen.filedatatransferagent.model.user;

public record UserIdentifier(
    String username,
    String firstName,
    String lastName,
    Integer age) {

}
