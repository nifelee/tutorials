package com.nifelee.user;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.core.Response;

import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.ClientsResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nifelee.common.AbstractKeycloakController;
import com.nifelee.util.KeycloakUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController extends AbstractKeycloakController {

  private final String role = "user";

  @PostMapping(path = "/create")
  public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
    UserRepresentation user = userDTO.toUserRepresentation();

    // Get realm
    RealmResource realmResource = getRealmResource();
    UsersResource usersResource = realmResource.users();

    try (Response response = usersResource.create(user)) {
      userDTO.setResponse(response);

      if (KeycloakUtil.isCreated(response)) {
        String userId = CreatedResponseUtil.getCreatedId(response);
        userDTO.setId(userId);

        createdUser(userDTO, realmResource, usersResource);
      }
    }

    return ResponseEntity.ok(userDTO);
  }

  private void createdUser(UserDTO userDTO, RealmResource realmResource, UsersResource usersResource) {
    UserResource userResource = resetPassword(userDTO, usersResource);

    if (userDTO.isGlobalRoleMapping()) {
      setGlobalRoleMapping(realmResource, userResource);
    } else {
      setClientRoleMapping(realmResource, userResource);
    }
  }

  private static UserResource resetPassword(UserDTO userDTO, UsersResource usersResource) {
    // create password credential
    CredentialRepresentation passwordCred = new CredentialRepresentation();
    passwordCred.setTemporary(false);
    passwordCred.setType(CredentialRepresentation.PASSWORD);
    passwordCred.setValue(userDTO.getPassword());

    UserResource userResource = usersResource.get(userDTO.getId());

    // Set password credential
    userResource.resetPassword(passwordCred);
    return userResource;
  }

  /*
   * Set Global realm roles
   */
  private void setGlobalRoleMapping(RealmResource realmResource, UserResource userResource) {
    RolesResource roles = realmResource.roles();
    RoleResource roleResource = roles.get(role);
    RoleRepresentation realmRoleUser = roleResource.toRepresentation();

    // Assign realm role student to user
    userResource.roles().realmLevel().add(Collections.singletonList(realmRoleUser));
  }

  /*
   * Set Client realm roles
   */
  private void setClientRoleMapping(RealmResource realmResource, UserResource userResource) {
    ClientsResource clientsResource = realmResource.clients();

    String clientUUID = null;
    ClientResource clientResource = null;
    String clientId = keycloakProperties.getResource();

    List<ClientRepresentation> clients = clientsResource.findByClientId(clientId);
    for (ClientRepresentation c : clients) {
      log.debug("Client id: {}, clientId: {}", c.getId(), c.getClientId());

      if (clientId.equals(c.getClientId())) {
        clientUUID = c.getId();
        clientResource = clientsResource.get(clientUUID);
        break;
      }
    }

    if (Objects.isNull(clientResource))
      throw new RuntimeException(String.format("Client[%s] not found..", clientId));

    RolesResource roles = clientResource.roles();

    RoleResource roleResource = roles.get(role);
    RoleRepresentation realmRoleUser = roleResource.toRepresentation();

    // Assign realm role student to user
    userResource.roles().clientLevel(clientUUID).add(Collections.singletonList(realmRoleUser));
  }

  @PostMapping(path = "/signin")
  public ResponseEntity<?> signin(@RequestBody UserDTO userDTO) {
    String clientSecret = keycloakProperties.getClientKeyPassword();
    String realm = keycloakProperties.getRealm();
    String clientId = keycloakProperties.getResource();

    Map<String, Object> clientCredentials = new HashMap<>();
    clientCredentials.put("secret", clientSecret);
    clientCredentials.put("grant_type", "password");

    Configuration configuration =
        new Configuration(keycloakProperties.getAuthServerUrl(), realm, clientId, clientCredentials, null);
    AuthzClient authzClient = AuthzClient.create(configuration);

    AccessTokenResponse response = authzClient.obtainAccessToken(userDTO.getEmail(), userDTO.getPassword());

    return ResponseEntity.ok(response);
  }

  @GetMapping(value = "/unprotected-data")
  public String getName() {
    return "Hello, this api is not protected.";
  }

  @GetMapping(value = "/protected-data")
  public String getEmail() {
    return "Hello, this api is protected.";
  }

}