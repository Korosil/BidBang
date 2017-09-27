/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import DAOs.BidDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author petros
 */
public class DeleteAuctionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int item_id;
        Boolean deleted;

        item_id = Integer.parseInt(request.getParameter("itemID"));

        deleted = BidDAO.deleteAuction(item_id);

        response.sendRedirect("DisplayAuctionsServlet");

    }

}
