package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // [status-line]
        response.setStatus(HttpServletResponse.SC_OK); // 200, success

        // [response-header]
        response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("my-header", "hello!");
        
        // [header 편의 메서드]
        content(response);
        cookie(response);
        redirect(response);

        response.getWriter().write("OK");
    }

    /* Content 편의 메서드 */
    private void content(HttpServletResponse response) {
        /* 아래와 동일
        response.setHeader("Content-Type", "text/plain;charset=utf-8"); */
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        //response.setContentLength(2); // 생략시 자동 생성, Content-Length: 2
    }

    /* Cookie 편의 메서드 */
    private void cookie(HttpServletResponse response) {
        // Set-Cookie: myCookie=good; Max-Age=600;
        /* 아래와 동일
        response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600"); */
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); //600초
        response.addCookie(cookie);
    }
    /* Redirect 편의 메서드 */
    private void redirect(HttpServletResponse response) throws IOException {
        // Status Code 302, Location: /basic/hello-form.html
        /* 아래와 동일
        response.setStatus(HttpServletResponse.SC_FOUND); //302, redirect
        response.setHeader("Location", "/basic/hello-form.html"); */
        response.sendRedirect("/basic/hello-form.html");
    }
}
