package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class TalksPage extends Page {
    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        User user = getUser();
        if (user == null) {
            throw new RedirectException("/index");
        }
    }
    private void sendMsg(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = getUser();


        userService.createEvent(user.getId(), Event.types.ENTER);
        setUser(user);
        setMessage("Hello, " + user.getLogin());
        throw new RedirectException("/index");

    }
//    @Override
//    protected void before(HttpServletRequest request,Map<String, Object>view){
//
//    }
//    @Override
//    protected void after(HttpServletRequest request, Map<String, Object> view) {
//        super.after(request, view);
//        User user = getUser();
//        view.put("users", userService.findAll());
////        view.put("talks", userService.findTalks(.getId()));
//    }
}
