package Servlets;

import DAOs.CategoryDAO;
import Beans.CategoryBean;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DisplayCategoryServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            CategoryBean category = new CategoryBean();
            category.setCategory(request.getParameter("category"));
            System.out.println("Category name: " + category.getCategory());
            category = CategoryDAO.returnCategoryItems(category);

            HttpSession session = request.getSession(true);
            session.setAttribute("CurrentCategory", category);
            response.sendRedirect("nav.jsp"); // Categories page

        } catch (Throwable theException) {
            System.out.println("Display Category failed:" + theException);
        }

    }
}
