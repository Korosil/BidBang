package Servlets;

import DAOs.ItemDAO;
import Beans.ItemBean;
import Beans.PhotoBean;
import Beans.UserBean;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.io.IOUtils;

@MultipartConfig(maxFileSize = 65000)    // upload file's size up to 65KB
public class AddAuctionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            ItemBean item = new ItemBean();

            item.setItemID();
            item.setName(request.getParameter("Name"));
            item.setCategory(request.getParameter("Category"));
            item.setFirst_bid(Float.parseFloat(request.getParameter("FirstBid")));
            item.setCurrently(item.getFirst_bid());
            item.setNum_bids(0);
            
            String buy_price = request.getParameter("BuyPrice");
            if (!buy_price.isEmpty()) {
                item.setBuy_price(Float.parseFloat(buy_price));
            }
            
            UserBean user = new UserBean();
            HttpSession session = request.getSession(true);
            user = (UserBean)session.getAttribute("currentSessionUser");
            item.setSeller_name(user.getUsername());
            item.setCity(request.getParameter("City"));
            item.setState(request.getParameter("State"));
            item.setCountry(request.getParameter("Country"));

            String[] Lat_Long = getLatLongPositions(item.getCity() + " " + item.getState() + " " + item.getCountry());
            item.setLatitude(Float.parseFloat(Lat_Long[0]));
            item.setLongtitude(Float.parseFloat(Lat_Long[1]));

            Date date = new Date();
            item.setStarted_date(date);
            item.setDescription(request.getParameter("Description"));
            
            item.setEnd_date(request.getParameter("EndDate"));
           
            PhotoBean photo = new PhotoBean();
            photo.setItemID(item.getItemID());
            
            InputStream inputStream = null; // input stream of the upload file

            // obtains the upload file part in this multipart request
            Part filePart = request.getPart("photo");
            if (filePart != null) {
                // prints out some information for debugging
                System.out.println(filePart.getName());
                System.out.println(filePart.getSize());
                System.out.println(filePart.getContentType());

                // obtains input stream of the upload file
                inputStream = filePart.getInputStream();
                byte[] image = IOUtils.toByteArray(inputStream); // Apache commons IO.
                photo.setPhoto(image);
            }
            
            item = ItemDAO.insertItemObject(item, photo);
            
            /*
                εάν μπήκε (πρέπει να δούμε με ποιόν τρόπο θα φαίνεται ότι είναι μέσα)
                    πέτα τη σέλιδα "Όλα κομπλέ"
                αλλιώς
                    πέτα τη σελίδα "Πρόβλημα"
             */
        } catch (Throwable theException) {
            System.out.println(theException);
        }

    }

    @SuppressWarnings("empty-statement")
    public static String[] getLatLongPositions(String address) throws Exception {
        int responseCode = 0;
        String api = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(address, "UTF-8") + "&sensor=true";
        System.out.println("URL : " + api);
        URL url = new URL(api);
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.connect();
        responseCode = httpConnection.getResponseCode();
        if (responseCode == 200) {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();;
            Document document = (Document) builder.parse(httpConnection.getInputStream());
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("/GeocodeResponse/status");
            String status = (String) expr.evaluate(document, XPathConstants.STRING);
            if (status.equals("OK")) {
                expr = xpath.compile("//geometry/location/lat");
                String latitude = (String) expr.evaluate(document, XPathConstants.STRING);
                expr = xpath.compile("//geometry/location/lng");
                String longitude = (String) expr.evaluate(document, XPathConstants.STRING);
                return new String[]{latitude, longitude};
            } else {
                throw new Exception("Error from the API - response status: " + status);
            }
        }
        return null;
    }
}
