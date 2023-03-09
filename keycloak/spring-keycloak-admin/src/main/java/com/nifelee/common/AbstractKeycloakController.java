package com.nifelee.common;

import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractKeycloakController {

  @Autowired
  private Keycloak keycloakAdmin;

  @Autowired
  protected KeycloakSpringBootProperties keycloakProperties;

  protected RealmResource getRealmResource() {
    String realm = keycloakProperties.getRealm();

    keycloakAdmin.tokenManager().getAccessToken();
    return keycloakAdmin.realm(realm);
  }

}
