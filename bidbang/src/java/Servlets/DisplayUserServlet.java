package Servlets;

import DAOs.UserDAO;
import Beans.UserBean;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DisplayUserServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            UserBean user = new UserBean();

            user.setUserName(request.getParameter("username"));
            System.out.println("the user you want to display is " + user.getUsername());

            user = UserDAO.displayUser(user);
            HttpSession session = request.getSession(true);
            session.setAttribute("Info", user);
            
            Float rating;
            rating = UserDAO.DisplayRating(user.getUsername());
            session.setAttribute("rating", rating);
            
            response.sendRedirect("user.jsp");

        } catch (Throwable theException) {
            System.out.println(theException);
        }

    }
}
