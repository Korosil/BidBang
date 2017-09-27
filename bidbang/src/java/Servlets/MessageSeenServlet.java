package Servlets;

import DAOs.MessageDAO;
import Beans.UserBean;
import Beans.SendMessageBean;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MessageSeenServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        UserBean user = new UserBean();
        HttpSession session = request.getSession(true);
        user = (UserBean) session.getAttribute("currentSessionUser");
        String username = user.getUsername();
        
        int message_id;
        int counter;
        message_id = Integer.parseInt(request.getParameter("messageID"));

        SendMessageBean message = new SendMessageBean();
        
        message = MessageDAO.seen(message_id);
        counter = MessageDAO.UnreadMessages(username);
        
        session.setAttribute("message", message);
        session.setAttribute("unread", counter);
        
        response.sendRedirect("readmessage.jsp");

    }

}
