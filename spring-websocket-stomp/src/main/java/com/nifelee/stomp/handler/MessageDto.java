package com.nifelee.stomp.handler;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageDto {
  private String roomId;

  private String nickname;

  private String message;
  private final long timestamp = new Date().getTime();

}
