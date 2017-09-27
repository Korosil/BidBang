package Servlets;

import DAOs.MessageDAO;
import Beans.MessageBean;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MessageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            MessageBean message = new MessageBean();
            message.setUsername(request.getParameter("user"));

            message = MessageDAO.returnMessages(message);
            
            HttpSession session = request.getSession(true);
            session.setAttribute("messages", message);
            response.sendRedirect("messages.jsp");

        } catch (Throwable theException) {
            System.out.println(theException);
        }

    }
}
