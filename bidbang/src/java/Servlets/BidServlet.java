package Servlets;

import DAOs.ItemDAO;
import DAOs.BidDAO;
import Beans.ItemBean;
import Beans.UserBean;
import Beans.BidBean;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class BidServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            BidBean bid = new BidBean();
            UserBean user = null;
            ItemBean item = null;

            HttpSession session = request.getSession(true);
            user = (UserBean) session.getAttribute("currentSessionUser");
            item = (ItemBean) session.getAttribute("Item");

            bid.setAuctionID(item.getItemID());
            bid.setUsername(user.getUsername());
            bid.setAmount(Float.parseFloat(request.getParameter("bid")));
            bid.setTime_of_bid(date);

            System.out.println("The current bid for this item is " + item.getCurrently());
            System.out.println("And you want to bid  " + bid.getAmount());
            bid = BidDAO.verifySetBid(bid);

            if (bid.getVerify() == 1) {

                System.out.println("your bid completed you are the current buyer");
                item = ItemDAO.returnItemObject(item);

                System.out.println("the item cunnet price is " + item.getCurrently());
                session.setAttribute("Item", item);
                response.sendRedirect("item.jsp");

            } else {
                System.out.println("you must place another bid :(");
                response.sendRedirect("item.jsp");
            }

        } catch (Throwable theException) {
            System.out.println(theException);
        }
    }
}
