package com.onlineenergyutilityplatform.websocket;

import com.onlineenergyutilityplatform.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Controller
@Slf4j
public class NotificationEndpoint {
    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/messageOnServer")
    public void message(Message message) {
        log.info("Message on websocket {} from user {} to user {}, type {}", message.getMessage(), message.getFromUserId(), message.getToUserId(), message.getType());
        template.convertAndSend("/serverPublish/messageOnClient/chat/" + message.getToUserId(), message);
    }
}
