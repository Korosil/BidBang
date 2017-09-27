package Servlets;

import DAOs.BidsHistoryDAO;
import Beans.BidsHistoryBean;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DisplayBidsHistoryServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            BidsHistoryBean history = new BidsHistoryBean();
            history.set_auctionID(parseInt(request.getParameter("itemID")));
            System.out.println("Item id that we're searching for is : " + history.get_auctionID());
            history = BidsHistoryDAO.returnHistory(history);

            HttpSession session = request.getSession(true);
            session.setAttribute("History", history);
            response.sendRedirect("bidshistory.jsp"); // Categories page
        } catch (Throwable theException) {
            System.out.println("Display History failed:" + theException);
        }
    }

}
