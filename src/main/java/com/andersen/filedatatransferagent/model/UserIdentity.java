package com.andersen.filedatatransferagent.model;

public record UserIdentity(
    String username,
    String firstName,
    String lastName,
    Integer age) {

}
