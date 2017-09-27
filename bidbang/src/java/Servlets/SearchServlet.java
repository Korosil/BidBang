package Servlets;

import DAOs.SearchDAO;
import Beans.SearchBean;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import javax.servlet.http.HttpSession;

public class SearchServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            ResultSet search_rs = null;
            SearchBean search_bean = new SearchBean();
            search_bean.setSearch_item(request.getParameter("searchterm"));
            search_bean = SearchDAO.search(search_bean);
            HttpSession session = request.getSession(true);
            session.setAttribute("CurrentSearch", search_bean);
            response.sendRedirect("results.jsp");
            
        } catch (Throwable theException) {
            System.out.println(theException);
        }
    }
}
