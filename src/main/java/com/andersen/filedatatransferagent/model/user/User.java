package com.andersen.filedatatransferagent.model.user;

import com.andersen.filedatatransferagent.model.workspace.Workspace;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private String username;
  private String firstName;
  private String lastName;
  private Integer age;
  private String street;
  private String city;
  private String state;
  private String zip;
  private String phoneNumber;
  private String email;
  private List<Workspace> workspaces;
}
