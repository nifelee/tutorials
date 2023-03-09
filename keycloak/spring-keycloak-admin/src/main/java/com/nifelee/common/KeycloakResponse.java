package com.nifelee.common;

import javax.ws.rs.core.Response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public abstract class KeycloakResponse {

  private int statusCode;
  private String status;

  public void setResponse(Response response) {
    statusCode = response.getStatus();
    status = response.getStatusInfo().toString();
  }

}
