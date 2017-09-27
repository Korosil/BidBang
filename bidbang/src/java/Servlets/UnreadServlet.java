package Servlets;

import DAOs.MessageDAO;
import Beans.UserBean;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UnreadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {            
        
        UserBean user = new UserBean();
        HttpSession session = request.getSession(true);
        user = (UserBean) session.getAttribute("currentSessionUser");
        
        
        if (user != null) {
            String username = user.getUsername();
            int counter = MessageDAO.UnreadMessages(username);
            
            session.setAttribute("unread", counter);
        }
    }

}
