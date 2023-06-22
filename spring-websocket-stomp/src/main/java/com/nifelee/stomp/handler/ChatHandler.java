package com.nifelee.stomp.handler;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ChatHandler {

  @MessageMapping("chat.join.{roomId}")
  @SendTo("/topic/message")
  public MessageDto join(@Payload MessageDto dto, SimpMessageHeaderAccessor headerAccessor) {
    dto.setMessage("입장..");
    log.debug("join: [{}]", dto);

    headerAccessor.getSessionAttributes().put("nickname", dto.getNickname());
    return dto;
  }

  @MessageMapping("chat.message.{roomId}")
  @SendTo("/topic/message")
  public MessageDto message(MessageDto dto) {
    log.debug("message: [{}]", dto);
    return dto;
  }

}
