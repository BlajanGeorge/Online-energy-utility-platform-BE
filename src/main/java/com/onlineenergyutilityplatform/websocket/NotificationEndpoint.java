package com.onlineenergyutilityplatform.websocket;

import com.onlineenergyutilityplatform.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Controller
@Slf4j
public class NotificationEndpoint {

    @MessageMapping("/messageOnServer")
    public void message(Message message) {
        log.info("Message on websocket {} from user {}", message.getMessage(), message.getUserId());
    }
}
