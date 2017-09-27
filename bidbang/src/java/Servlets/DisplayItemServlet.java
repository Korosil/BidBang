package Servlets;

import DAOs.ItemDAO;
import Beans.ItemBean;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DisplayItemServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            ItemBean item = new ItemBean();
            item.setItemID(parseInt(request.getParameter("itemID")));
            System.out.println("itemID: " + item.getItemID());
            item = ItemDAO.returnItemObject(item);

            HttpSession session = request.getSession(true);
            session.setAttribute("Item", item);
            response.sendRedirect("item.jsp"); // Item page
            
        } catch (Throwable theException) {
            System.out.println(theException);
        }

    }
}
