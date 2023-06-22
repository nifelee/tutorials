package com.nifelee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatController {

  @GetMapping("/room")
  public String room(String id, String nickname, Model model) {
    model.addAttribute("roomId", id);
    model.addAttribute("nickname", nickname);
    return "chat/room";
  }

}
