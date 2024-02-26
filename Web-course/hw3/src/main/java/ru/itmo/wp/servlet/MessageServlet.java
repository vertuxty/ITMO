package ru.itmo.wp.servlet;

import com.google.gson.Gson;
import ru.itmo.wp.Entity.MessageEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageServlet extends HttpServlet {
    public static final String USER = "user";
    public static final String TEXT = "text";
    List<MessageEntity> messageList = new ArrayList<>();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String uri = request.getRequestURI();
        HttpSession session = request.getSession();
        switch (uri) {
            case "/message/auth":
                messageAuth(response, request, session);
                break;
            case "/message/findAll":
                messageFindAll(response, session);
                break;
            case "/message/add":
                messageAdd(request, session);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }


    private void messageAuth(HttpServletResponse response, HttpServletRequest request, HttpSession session)
            throws IOException {
        response.setContentType("application/json");
        String user = request.getParameter(USER);
        if (user != null && !user.trim().isEmpty()) {
            session.setAttribute(USER, user);
        }
        printJson(response, session.getAttribute(USER));
    }

    private void messageFindAll(HttpServletResponse response, HttpSession session)
            throws IOException {
        response.setContentType("application/json");
        if (session.getAttribute(USER) != null) {
            printJson(response, messageList);
        }
    }

    private void messageAdd(HttpServletRequest request, HttpSession session) {
        String text = request.getParameter(TEXT);
        String user = (String) session.getAttribute(USER);
        if (!text.trim().isEmpty()) {
            messageList.add(new MessageEntity(user, text));
        }
    }


    private void printJson(HttpServletResponse response, Object obj) throws IOException {
        String json = new Gson().toJson(obj);
        response.getWriter().print(json);
        response.getWriter().flush();
    }
}
