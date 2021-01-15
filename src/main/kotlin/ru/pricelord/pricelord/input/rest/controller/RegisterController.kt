package ru.pricelord.pricelord.input.rest.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class RegisterController {


    @RequestMapping("/register")
    fun registerView():String {
        return "register"
    }
}