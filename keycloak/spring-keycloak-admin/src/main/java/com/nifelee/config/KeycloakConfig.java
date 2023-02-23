package com.nifelee.config;

import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class KeycloakConfig {

  private final KeycloakSpringBootProperties keycloakProperties;
  private Map<?, ?> config;

  @PostConstruct
  public void init() {
    config = keycloakProperties.getConfig();
  }

  @Bean
  public Keycloak keycloakAdmin() {
    return KeycloakBuilder.builder()
      .serverUrl(keycloakProperties.getAuthServerUrl())
      .grantType(OAuth2Constants.PASSWORD)
      .realm("master")
      .clientId("admin-cli")
      .username(getConfig("admin-username"))
      .password(getConfig("admin-password"))
      .resteasyClient(new ResteasyClientBuilderImpl().connectionPoolSize(10).build())
      .build();
  }

  private String getConfig(String key) {
    Object value = config.get(key);
    return Objects.isNull(value) ? null : (String)value;
  }

}
