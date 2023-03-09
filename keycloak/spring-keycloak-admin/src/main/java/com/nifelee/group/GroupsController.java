package com.nifelee.group;

import javax.ws.rs.core.Response;

import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nifelee.common.AbstractKeycloakController;
import com.nifelee.util.KeycloakUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/groups")
@Slf4j
public class GroupsController extends AbstractKeycloakController {

  @PostMapping(path = "/create")
  public ResponseEntity<GroupDTO> createGroup(@RequestBody GroupDTO groupDTO) {
    RealmResource realm = getRealmResource();
    GroupRepresentation group = groupDTO.toGroupRepresentation();

    try (Response response = realm.groups().add(group)) {
      groupDTO.setResponse(response);

      String groupId = KeycloakUtil.getCreatedId(response);
      groupDTO.setId(groupId);
    }

    return ResponseEntity.ok(groupDTO);
  }

  @PostMapping(path = "/{groupId}/create")
  public ResponseEntity<GroupDTO> createSubGroup(@PathVariable String groupId, @RequestBody GroupDTO groupDTO) {
    RealmResource realm = getRealmResource();
    GroupRepresentation subgroup = groupDTO.toGroupRepresentation();

    try (Response response = realm.groups().group(groupId).subGroup(subgroup)) {
      groupDTO.setResponse(response);

      String subGroupId = KeycloakUtil.getCreatedId(response);
      groupDTO.setId(subGroupId);
    }

    return ResponseEntity.ok(groupDTO);
  }

  @DeleteMapping(path = "/{groupId}")
  public ResponseEntity<Void> removeGroup(@PathVariable String groupId) {
    RealmResource realm = getRealmResource();
    realm.groups().group(groupId).remove();

    return ResponseEntity.noContent().build();
  }

  @PutMapping(path = "/{groupId}/users/{userId}")
  public ResponseEntity<Void> addUser(@PathVariable String groupId, @PathVariable String userId) {
    RealmResource realm = getRealmResource();
    realm.users().get(userId).joinGroup(groupId);

    return ResponseEntity.noContent().build();
  }

  @DeleteMapping(path = "/{groupId}/users/{userId}")
  public ResponseEntity<Void> leaveGroupUser(@PathVariable String groupId, @PathVariable String userId) {
    RealmResource realm = getRealmResource();
    realm.users().get(userId).leaveGroup(groupId);

    return ResponseEntity.noContent().build();
  }

}
