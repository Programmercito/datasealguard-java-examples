package org.osbo.demos;

import org.osbo.demos.model.entities.User;

import jakarta.servlet.http.HttpSession;

/**
 *
 * @author programmercito
 */
public class SessionSystem {

    public static User getCurrent(HttpSession session) {
        User resul = (User) session.getAttribute("user");
        return resul;
    }

}
