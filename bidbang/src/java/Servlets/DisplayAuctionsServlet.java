package Servlets;

import DAOs.BidDAO;
import Beans.DisplayAuctionsBean;
import Beans.UserBean;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DisplayAuctionsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            UserBean user;
            String username;

            HttpSession session = request.getSession(true);

            user = (UserBean)session.getAttribute("currentSessionUser");
            username = user.getUsername();
            
            DisplayAuctionsBean display_auctions = new DisplayAuctionsBean();
            display_auctions.setUsername(username);
            display_auctions = BidDAO.DisplayAuctions(display_auctions);

            session.setAttribute("auctions", display_auctions);
            response.sendRedirect("auctions.jsp");
            
        } catch (Throwable theException) {
            System.out.println(theException);
        }

    }

}
