package com.onlineenergyutilityplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@SpringBootApplication
public class OnlineEnergyUtilityPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineEnergyUtilityPlatformApplication.class, args);
    }

}
