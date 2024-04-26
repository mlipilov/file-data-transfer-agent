package com.andersen.filedatatransferagent.model.user;

public record User(
    UserIdentity identity,
    UserAddress address,
    UserContactDetails contactDetails) {

}
