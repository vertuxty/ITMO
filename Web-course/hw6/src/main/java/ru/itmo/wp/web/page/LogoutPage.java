package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public class LogoutPage extends Page {
    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        User user = getUser();
        if (user != null) {
            System.out.println(user.getId() + " " + user.getLogin() + " " + user.getEmail() + " " + user.getCreationTime());
            userService.createEvent(user.getId(), Event.types.LOGOUT);
            removeUser();
            setMessage("Good byeee. Hope to see you soon!");
        }
        throw new RedirectException("/index");
    }
}
