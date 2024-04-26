package com.andersen.filedatatransferagent.model;

public record User(
    UserIdentity identity,
    UserAddress address,
    UserContactDetails contactDetails) {

}
