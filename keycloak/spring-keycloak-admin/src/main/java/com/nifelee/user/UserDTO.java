package com.nifelee.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {

  private String email;
  private String password;
  private String firstname;
  private String lastname;
  private int statusCode;
  private String status;

}
