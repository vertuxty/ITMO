package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class StaticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] uriList = request.getRequestURI().split("\\+");
        System.out.println(request.getServletContext().getRealPath("."));
//        if (request.getSession().getAttribute("passed").equals("true")) {
            for (String s : uriList) {
                File file = find(s);
                if (file.isFile()) {
                    String type = response.getContentType() == null ? getServletContext().getMimeType(file.getName()) : response.getContentType();
                    response.setContentType(type);
                    try (OutputStream outputStream = response.getOutputStream()) {
                        outputStream.write(Files.readAllBytes(file.toPath()));
                        outputStream.flush();
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
//        }
    }
    private File find(String uri) {
        File newFile = new File(getServletContext().getRealPath("."), "../../src/main/webapp/static/" + uri);
        if (newFile.isFile()) {
            return newFile;
        }
        return (new File(getServletContext().getRealPath("/static/" + uri)));
    }
}
