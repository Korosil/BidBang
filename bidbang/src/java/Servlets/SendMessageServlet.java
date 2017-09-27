package Servlets;

import DAOs.MessageDAO;
import Beans.ItemBean;
import Beans.UserBean;
import Beans.SendMessageBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class SendMessageServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            SendMessageBean message = new SendMessageBean();
            UserBean user;
            String username;
            ItemBean item;
            String body;
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDate = dateFormat.format(date);

            HttpSession session = request.getSession(true);

            user = (UserBean) session.getAttribute("currentSessionUser");
            item = (ItemBean) session.getAttribute("Item");
            body = request.getParameter("body");

            message.setFrom_user(user.getUsername());
            message.setTo_user(item.getSeller_name());
            message.setMessage(body);
            message.setIsseen(false);
            message.setMessage_id();
            message.setDate(currentDate);

            MessageDAO.insertMessage(message);

            response.sendRedirect("messagesend.jsp");
        } catch (Throwable theException) {
            System.out.println(theException);
        }

    }

}
