package ru.itmo.web.hw4.util;

import ru.itmo.web.hw4.model.Color;
import ru.itmo.web.hw4.model.Post;
import ru.itmo.web.hw4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class DataUtil {

    private static final List<Post> POSTS = Arrays.asList(
            new Post(2, "All the good Girls goes to Hell!", DataTextsUtil.TEXT_1, 1, "1 day ago"),
            new Post(3, "Over my head by Sum 41", DataTextsUtil.TEXT_2, 6, "3 days ago"),
            new Post(7, "Kill DJ by Green Day", DataTextsUtil.TEXT_3, 6, "4 days ago"),
            new Post(11, "Why'd You Only Call Me When You're High? by Arctic Monkeys", DataTextsUtil.TEXT_4, 11, "6 days ago")
    );

    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", Color.RED),
            new User(6, "pashka", "Pavel Mavrin", Color.BLUE),
            new User(9, "geranazavr555", "Georgiy Nazarov", Color.GREEN),
            new User(11, "tourist", "Gennady Korotkevich", Color.RED)
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("posts", POSTS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }
}
