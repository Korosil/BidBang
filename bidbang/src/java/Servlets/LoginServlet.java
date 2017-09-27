package Servlets;

import DAOs.UserDAO;
import Beans.UserBean;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {

            UserBean user = new UserBean();
            user.setUserName(request.getParameter("user"));
            user.setPassword(request.getParameter("pass"));

            user = UserDAO.login(user);

            if (user.isValid()) {

                HttpSession session = request.getSession(true);
                session.setAttribute("currentSessionUser", user);
                if (Objects.equals(user.getUsername(), "admin")) {
                    response.sendRedirect("UnverifiedUsersServlet"); // administration panel
                } else {
                    response.sendRedirect("index.jsp"); //user logged-in page 
                }
            } else {
                response.sendRedirect("login.jsp"); //error page 
            }
        } catch (Throwable theException) {
            System.out.println(theException);
        }
    }
}
