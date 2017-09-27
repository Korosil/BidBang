package Servlets;

import DAOs.PhotoDAO;
import Beans.PhotoBean;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/images/*")
public class PhotoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            int itemID = Integer.parseInt(request.getPathInfo().substring(1));
            Statement stmt = null;
            ResultSet rs = null;

            PhotoBean photo = new PhotoBean();
            photo.setItemID(itemID);
            photo = PhotoDAO.returnPhoto(photo);

            response.setContentType("image/jpeg");
            response.setContentLength(photo.getPhoto().length);
            response.getOutputStream().write(photo.getPhoto());

        } catch (Throwable theException) {
            System.out.println(theException);
        }

    }

}
