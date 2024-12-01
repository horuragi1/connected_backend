package com.example.demo.controller;

import com.example.demo.service.PlaylistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class PlaylistController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private PlaylistService playlistService;

}
