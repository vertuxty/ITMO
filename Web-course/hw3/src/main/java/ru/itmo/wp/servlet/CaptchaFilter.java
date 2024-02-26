package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

public class CaptchaFilter extends HttpFilter {

    private final String PASSED = "passed";
    private final String CORRECT_CAPTCHA = "correctCaptcha";
    private final String CAPTCHA = "captcha";

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        String passed = (String) session.getAttribute(PASSED);
        if (passed != null) {
            super.doFilter(request, response, chain);
            return;
        }
        String correctCaptcha = (String) session.getAttribute(CORRECT_CAPTCHA);
        if (correctCaptcha == null) {
            createCaptcha(session, response, request.getRequestURI());
        } else {
            String answer = request.getParameter(CAPTCHA);
            if (answer != null && answer.equals(correctCaptcha)) {
                session.setAttribute(PASSED, "true");
//                response.setCharacterEncoding(StandardCharsets.UTF-8);
                response.sendRedirect(request.getRequestURI());
            } else {
                createCaptcha(session, response, request.getRequestURI());
            }
        }
    }

    private void createCaptcha(HttpSession session, HttpServletResponse response, String uri) throws IOException {
        int randomNum = ThreadLocalRandom.current().nextInt(100, 1000);
        byte[] img = ImageUtils.toPng(String.valueOf(randomNum));
        session.setAttribute(CORRECT_CAPTCHA, String.valueOf(randomNum));
        printCaptcha(response, uri, img);
    }

    private void printCaptcha(HttpServletResponse response, String uri, byte[] img) throws IOException {
        response.setContentType("text/html");
        String base64Encode = Base64.getEncoder().encodeToString(img);
        PrintWriter writer = response.getWriter();
        writer.print("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Verification</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<img src=\"data:image/png;base64," + base64Encode + "\">\n" +
                "<form id=\"captcha_confirm\" action=\"" + uri +  "\" method=\"get\">\n" +
                "   <p>\n" +
                "       <label for=\"value1\">Write captcha:</label>\n" +
                "       <input name=\"captcha\" id=\"value1\">\n" +
                "   </p>\n" +
                "</form>\n" +
                "<button type=\"submit\" form=\"captcha_confirm\">Submit</button>\n" +
                "</body>\n" +
                "</html>");
        writer.flush();
        writer.close();
    }
}
