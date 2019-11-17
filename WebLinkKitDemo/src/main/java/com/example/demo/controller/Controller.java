package com.example.demo.controller;

import com.example.demo.service.getMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private getMessageService getMessageService;

    @GetMapping("/get/status")
    public int getStatus()
    {
        return getMessageService.getStatus();
    }

}
