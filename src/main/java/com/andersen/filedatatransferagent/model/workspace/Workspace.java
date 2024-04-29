package com.andersen.filedatatransferagent.model.workspace;

import java.time.LocalDate;

public record Workspace(
    WorkspaceIdentifier identifier,
    String name,
    LocalDate creationDate,
    String description) {

}
