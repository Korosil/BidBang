package Servlets;

import DAOs.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RatingServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Float rating;
        rating = Float.parseFloat(request.getParameter("rating"));
        String username;

        HttpSession session = request.getSession(true);
        username = (String) session.getAttribute("user");

        UserDAO.rateUser(rating, username);

        response.sendRedirect("user.jsp");

    }

}
