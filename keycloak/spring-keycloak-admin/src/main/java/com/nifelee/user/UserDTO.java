package com.nifelee.user;

import java.util.Objects;

import org.keycloak.representations.idm.UserRepresentation;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {

  private String username;
  private String email;
  private String password;
  private String firstname;
  private String lastname;
  private int statusCode;
  private String status;

  @JsonIgnore
  private String globalRole;

  public UserRepresentation toUserRepresentation() {
    UserRepresentation user = new UserRepresentation();

    user.setEnabled(true);
    user.setUsername(getUsernameEmptyEmail());
    user.setEmail(email);
    user.setFirstName(firstname);
    user.setLastName(lastname);

    return user;
  }

  private String getUsernameEmptyEmail() {
    return Objects.isNull(username) ? email : username;
  }

  @JsonIgnore
  public boolean isGlobalRoleMapping() {
    return Objects.nonNull(globalRole) && "Y".equals(globalRole);
  }

}
