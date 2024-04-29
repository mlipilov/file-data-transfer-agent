package com.andersen.filedatatransferagent.model.user;

import com.andersen.filedatatransferagent.model.workspace.Workspace;
import java.util.List;

public record User(
    UserIdentifier identifier,
    UserAddress address,
    UserContactDetails contactDetails,
    List<Workspace> workspaces) {

}
