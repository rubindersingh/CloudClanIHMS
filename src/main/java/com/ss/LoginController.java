package com.ss;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rubinder on 10/24/16.
 */
@RestController
public class LoginController {

    @RequestMapping("/myrest")
    public String index(Model model) {
        return "hello hello";
    }

}
