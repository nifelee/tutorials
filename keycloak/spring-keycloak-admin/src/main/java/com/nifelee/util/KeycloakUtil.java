package com.nifelee.util;

import java.net.URI;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.keycloak.admin.client.CreatedResponseUtil;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class KeycloakUtil {

  /**
   * @see org.keycloak.admin.client.CreatedResponseUtil#getCreatedId(Response)
   */
  public static String getCreatedId(Response response) {
    URI location = response.getLocation();

    if (response.getStatusInfo().equals(Response.Status.CREATED)) {
      String path = location.getPath();
      String createdId = path.substring(path.lastIndexOf('/') + 1);
      log.debug("createdId: {}", createdId);
      return createdId;
    } else {
      Response.StatusType statusInfo = response.getStatusInfo();
      log.warn("Create returned status: {} (Code: {}); expected status: Created (201)",
          statusInfo.getReasonPhrase(), statusInfo.getStatusCode());

      return null;
    }
  }

  public static boolean isCreated(Response response) {
    return response.getStatusInfo().equals(Response.Status.CREATED);
  }

}
