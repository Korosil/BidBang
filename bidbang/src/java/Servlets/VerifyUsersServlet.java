package Servlets;

import DAOs.UnverifiedUsersDAO;
import Beans.UnverifiedUsersBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class VerifyUsersServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {

            UnverifiedUsersBean UsersToBeVerified = new UnverifiedUsersBean();

            String[] values = request.getParameterValues("UnverifiedUsers");
            List<String> users = new ArrayList<String>(Arrays.asList(values));
            UsersToBeVerified.setUnverifiedUsers_rs((ArrayList<String>)users);

            UnverifiedUsersDAO.verifyUsers(UsersToBeVerified);

            response.sendRedirect("UnverifiedUsersServlet");

        } catch (Throwable theException) {
            System.out.println(theException);
        }

    }

}
