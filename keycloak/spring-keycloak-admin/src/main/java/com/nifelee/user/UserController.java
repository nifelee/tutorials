package com.nifelee.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

  private final Keycloak keycloakAdmin;
  private final KeycloakSpringBootProperties keycloakProperties;

  private String authServerUrl = "http://localhost:7070";
  private String role = "student";

  @PostMapping(path = "/create")
  public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
    keycloakAdmin.tokenManager().getAccessToken();

    UserRepresentation user = new UserRepresentation();
    user.setEnabled(true);
    user.setUsername(userDTO.getEmail());
    user.setFirstName(userDTO.getFirstname());
    user.setLastName(userDTO.getLastname());
    user.setEmail(userDTO.getEmail());

    // Get realm
    String realm = keycloakProperties.getRealm();
    RealmResource realmResource = keycloakAdmin.realm(realm);
    UsersResource usersResource = realmResource.users();

    try (Response response = usersResource.create(user)) {
      userDTO.setStatusCode(response.getStatus());
      userDTO.setStatus(response.getStatusInfo().toString());

      if (response.getStatus() == 201) {
        String userId = CreatedResponseUtil.getCreatedId(response);
        log.info("Created userId {}", userId);

        // create password credential
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(userDTO.getPassword());

        UserResource userResource = usersResource.get(userId);

        // Set password credential
        userResource.resetPassword(passwordCred);

        // Get realm role student
        RolesResource roles = realmResource.roles();
        RoleResource roleResource = roles.get(role);
        RoleRepresentation realmRoleUser = roleResource.toRepresentation();

        // Assign realm role student to user
        userResource.roles().realmLevel().add(Collections.singletonList(realmRoleUser));
      }
    }

    return ResponseEntity.ok(userDTO);
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
      new Configuration(authServerUrl, realm, clientId, clientCredentials, null);
    AuthzClient authzClient = AuthzClient.create(configuration);

    AccessTokenResponse response =
      authzClient.obtainAccessToken(userDTO.getEmail(), userDTO.getPassword());

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