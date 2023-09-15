package org.osbo.demos.controller;

import jakarta.servlet.http.HttpSession;
import org.osbo.demos.model.entities.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserControlerFake {

    @PostMapping
    public User init(@RequestBody User usuario, HttpSession session) {
        session.setAttribute("user", usuario);
        return usuario;
    }
}
