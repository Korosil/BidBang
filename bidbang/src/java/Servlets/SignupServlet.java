package Servlets;

import DAOs.UserDAO;
import Beans.UserBean;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignupServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse 
        response) throws ServletException, IOException {

        try {

            UserBean user = new UserBean();
            
            user.setUserName(request.getParameter("Username"));
            user.setPassword(request.getParameter("Password"));
            user.setName(request.getParameter("FirstName"));
            user.setSurname(request.getParameter("LastName"));
            user.setEmail(request.getParameter("Email"));
            user.setPhonenumber(request.getParameter("PhoneNumber"));
            user.setAddress(request.getParameter("Street"));
            user.setCity(request.getParameter("City"));
            user.setState(request.getParameter("State"));
            user.setCountry(request.getParameter("Country"));
            user.setZipcode(request.getParameter("ZipCode"));
            user.setTrn(request.getParameter("TRN"));

            user = UserDAO.signup(user);

            if (user.isValid()) {

                HttpSession session = request.getSession(true);
                session.setAttribute("currentSessionUser", user);
                response.sendRedirect("index.jsp"); //logged-in page      		
            } else {
                HttpSession session = request.getSession(true);
                session.setAttribute("Msg", "Το όνομα χρήστη ή το email χρησιμοποιούνται ήδη");
                response.sendRedirect("signup.jsp"); //error page 
            }
        } catch (Throwable theException) {
            System.out.println(theException);
        }
    }
}
