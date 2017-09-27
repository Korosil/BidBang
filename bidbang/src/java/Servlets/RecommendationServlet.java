package Servlets;

import DAOs.RecommendationDAO;
import Beans.UserBean;
import Beans.RecommendationBean;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RecommendationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings({"unchecked", "rawtypes"})

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        Object[] a = null;
        RecommendationBean recommendation_bean = new RecommendationBean();
        ArrayList<Integer> recommended = new ArrayList<Integer>();

        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("currentSessionUser");
        
        if (user != null) {
            String username = user.getUsername();

            a = RecommendationDAO.mutuality(username);
            if (a == null) {
                System.out.println("either the user has not made a bid or there are no mutual users");
                recommendation_bean = null;
            } else {
                System.out.println("there are mutual users!");
                recommended = RecommendationDAO.recommended_items(a, username);
                recommendation_bean = RecommendationDAO.returnInfos(recommended);
            }

            request.setAttribute("rec_bean", recommendation_bean);
        }

//        RequestDispatcher dispatcher = request.getRequestDispatcher("nav.jsp");
//	dispatcher.forward(request,response);
        //closing the result sets
        if (RecommendationDAO.mybids != null) {
            try {
                RecommendationDAO.mybids.close();
            } catch (Exception e) {
            }
            RecommendationDAO.mybids = null;
        }
        if (RecommendationDAO.mutuals != null) {
            try {
                RecommendationDAO.mutuals.close();
            } catch (Exception e) {
            }
            RecommendationDAO.mutuals = null;
        }
        if (RecommendationDAO.finalres != null) {
            try {
                RecommendationDAO.finalres.close();
            } catch (Exception e) {
            }
            RecommendationDAO.finalres = null;
        }
        if (RecommendationDAO.currentCon != null) {
            try {
                RecommendationDAO.currentCon.close();
            } catch (Exception e) {
            }

            RecommendationDAO.currentCon = null;
        }

    }
}
