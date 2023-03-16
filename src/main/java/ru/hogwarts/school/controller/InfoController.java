package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    private final ServerProperties serverProperties;
    private final Logger logger = LoggerFactory.getLogger(InfoController.class);


    @Value("${local.server.port}") private int port;

    public InfoController(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @GetMapping("/port")
    public int getPortNumber() {
        return port;
    }
}
