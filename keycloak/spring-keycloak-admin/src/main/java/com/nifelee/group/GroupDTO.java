package com.nifelee.group;

import org.keycloak.representations.idm.GroupRepresentation;

import com.nifelee.common.KeycloakResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GroupDTO extends KeycloakResponse {

  private String id;
  private String name;

  public GroupRepresentation toGroupRepresentation() {
/*
    String name;
    String path;
    Map<String, List<String>> attributes;
    List<String> realmRoles;
    Map<String, List<String>> clientRoles;
    List<GroupRepresentation> subGroups;
    Map<String, Boolean> access;
*/
    GroupRepresentation group = new GroupRepresentation();
    group.setName(name);
    return group;
  }

}
