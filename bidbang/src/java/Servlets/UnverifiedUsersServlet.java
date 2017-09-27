package Servlets;

import DAOs.UnverifiedUsersDAO;
import Beans.UnverifiedUsersBean;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UnverifiedUsersServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            
            UnverifiedUsersBean unverifiedUsers = new UnverifiedUsersBean();
            unverifiedUsers = UnverifiedUsersDAO.unverifiedUsers(unverifiedUsers);
            
            HttpSession session = request.getSession(true);
            session.setAttribute("UnverifiedUsers", unverifiedUsers);

            response.sendRedirect("admin.jsp");

        } catch (Throwable theException) {
            System.out.println(theException);
        }

    }

}
