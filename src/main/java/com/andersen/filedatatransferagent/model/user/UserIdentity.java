package com.andersen.filedatatransferagent.model.user;

public record UserIdentity(
    String username,
    String firstName,
    String lastName,
    Integer age) {

}
