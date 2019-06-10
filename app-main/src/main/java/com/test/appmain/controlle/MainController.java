package com.test.appmain.controlle;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/{userId}")
    public String getUserInfoById(@PathVariable("userId") String userId) {
        return "user";
    }
}
