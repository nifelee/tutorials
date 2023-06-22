package com.nifelee.stomp.handler;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler {

  private final SimpMessageSendingOperations messageTemplate;

  @EventListener
  public void connectEvent(SessionConnectEvent event){
    log.debug("연결 성공..");
    log.debug("{}", event);
  }

  @EventListener
  public void onDisconnectEvent(SessionDisconnectEvent event) {
    log.debug("연결 끊어짐..");
    log.debug("{}", event);

    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
    String nickname = (String) headerAccessor.getSessionAttributes().get("nickname");

    if (nickname != null) {
      MessageDto chatMessage = new MessageDto();

      chatMessage.setMessage("[" + nickname + "] 퇴장..");
      chatMessage.setNickname(nickname);

      messageTemplate.convertAndSend("/topic/message", chatMessage);
    }
  }

}
