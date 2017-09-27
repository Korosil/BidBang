<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="Beans.UserBean" import="Beans.ItemBean" import="java.util.Objects" import="java.util.Date" import="java.text.SimpleDateFormat" import="java.text.DateFormat"%>

<!DOCTYPE html>

<html>

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <%
            ItemBean Item = (ItemBean) (session.getAttribute("Item"));
        %>

        <title><%=Item.getName()%> | BidBang.gr</title>

        <!-- Bootstrap Core CSS -->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom Fonts -->
        <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>
        <link href='https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>

        <!-- Plugin CSS -->
        <link href="vendor/magnific-popup/magnific-popup.css" rel="stylesheet">

        <!-- Theme CSS -->
        <link href="css/creative.css" rel="stylesheet">

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

        <script src="http://www.openlayers.org/api/OpenLayers.js"></script>
        <script>
            function init() {
                map = new OpenLayers.Map("mapdiv");
                map.addLayer(new OpenLayers.Layer.OSM());

                var lonLat = new OpenLayers.LonLat(<%=Item.getLongtitude()%>, <%=Item.getLatitude()%>)
                        .transform(
                                new OpenLayers.Projection("EPSG:4326"), // transform from WGS 1984
                                map.getProjectionObject() // to Spherical Mercator Projection
                                );

                var zoom = 11;

                var markers = new OpenLayers.Layer.Markers("Markers");
                map.addLayer(markers);

                markers.addMarker(new OpenLayers.Marker(lonLat));

                map.setCenter(lonLat, zoom);
            }
        </script>

        <style>
            #mapdiv { width:100%; height:300px; }
            div.olControlAttribution { bottom:3px; }
        </style>

    </head>

    <!-- Login Modal -->
    <div class="modal fade" id="login-modal" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-sm" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 align="center" class="modal-title">Σύνδεση</h4>
                </div>
                <div class="modal-body">
                    <form action="LoginServlet">
                        <div class="form-group">
                            <input type="text" class="form-control" name="user" placeholder="Όνομα χρήστη">
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control" name="pass" placeholder="Συνθηματικό">
                        </div>
                        <button type="submit" class="btn btn-primary btn-block">Σύνδεση</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Navigation Bar -->
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" style="color: #f68a22" href="index.jsp">BidBang</a>
                </div>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <div class="nav navbar-nav">
                    <form class="navbar-form" method="post" action="SearchServlet">
                        <div class="input-group">
                            <input type="search" name="searchterm" class="form-control" style="height: 32px" placeholder="γράψε τον όρο αναζήτησης">
                            <span class="input-group-btn">
                                <button class="btn btn-primary" type="submit">Αναζήτηση</button>
                            </span>
                        </div>
                    </form>
                </div>
                <div class="nav navbar-nav navbar-right">
                    <%
                        UserBean currentUser = (UserBean) (session.getAttribute("currentSessionUser"));
                        if (currentUser == null) {
                    %>
                    <p class="navbar-text">
                        <a href="#" data-toggle="modal" data-target="#login-modal">Σύνδεση</a> | 
                        <a href="signup.jsp">Γίνε μέλος</a>
                    </p>
                    <%} else { %>
                    <div style="display: inline-block; padding: 17px">
                        <span>Καλωσόρισες </span><span class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" <%if (currentUser.getVerified() == false) {%>style="color:#9d9d9d"<%}%>><%=currentUser.getUsername()%> <span class="caret"></span></a>
                            <ul class="dropdown-menu pull-right">
                                <% if (Objects.equals(currentUser.getUsername(), "admin")) { %>
                                <li><a href="UnverifiedUsersServlet">Διαχείριση χρηστών</a></li>

                                <% } else if (currentUser.getVerified() == true) { %>
                                <li><a href="DisplayAuctionsServlet">Διαχείριση δημοπρασιών</a></li>
                                <li><a href="addauction.jsp">Προσθήκη δημοπρασίας</a></li>
                                <% int unread = (Integer) session.getAttribute("unread");%>
                                <li><a href="MessageServlet?user=<%=currentUser.getUsername()%>">Μηνύματα <%if(unread > 0){%><span class="badge"><%=unread%></span><%}%></a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="LogoutServlet">Αποσύνδεση</a></li>
                                    <% } else {%>
                                <li><a href="LogoutServlet">Αποσύνδεση</a></li>
                                    <% } %>
                            </ul>
                        </span>
                    </div>
                    <% }%>

                </div>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>

    <body onload="init();">
        <section class="bg-info">
            <div class="container">
                <div class="row">
                    <h3><strong><%=Item.getName()%></strong></h3><br>
                    <div class="col-md-4">
                        <img id="image" src="${pageContext.request.contextPath}/images/<%=Item.getItemID()%>" data-zoom-image="${pageContext.request.contextPath}/images/<%=Item.getItemID()%>" class="img-thumbnail" style="width: 100%; height: auto;">
                    </div>
                    <div class="col-md-8">

                        <p><script language="JavaScript">TargetDate = "<%=Item.getEnd_date()%>"; BackColor = "transparent"; ForeColor = "#333"; CountActive = true; CountStepper = -1; LeadingZero = false; DisplayFormat = "Απομένουν %%D%%η %%H%%ω %%M%%λ"; FinishMessage = "Πωλήθηκε στον χρήστη <%=Item.getCurrent_buyer_username()%>";</script><script language="JavaScript" src="//scripts.hashemian.com/js/countdown.js"></script></p>
                        <%
                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            Date curdate = new Date();
                            System.out.println(curdate);
                            String date = Item.getEnd_date();
                            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            Date enddate = format.parse(date);
                            System.out.println(enddate);
                            int finish = curdate.compareTo(enddate);
                            if (finish < 0) {
                                if (Item.getNum_bids() == 0) {
                        %>
                        <p>Αρχική προσφορά: <span style="font-size: x-large"><strong>EUR <%=Item.getCurrently()%></strong></span> <small>[<%=Item.getNum_bids()%> προσφορές]</small></p>   
                        <%
                                } else {
                        %>
                        <p>Τρέχουσα προσφορά: <strong>EUR <%=Item.getCurrently()%></strong> <small>[<a href="DisplayBidsHistoryServlet?itemID=<%=Item.getItemID()%>"  onclick="window.open('DisplayBidsHistoryServlet?itemID=<%=Item.getItemID()%>', 'newwindow', 'width=770, height=400'); return false;"><%=Item.getNum_bids()%> προσφορές</a>]</small></p>
                        <%
                                }
                                if (currentUser == null) {
                        %>
                        <form method="post" action="" class="form-inline"><input type="number" class="form-control" name="bid">  <button type="submit" class="btn btn-danger" disabled="disabled">Κάνε προσφορά</button></form>
                        <p class="text-danger"><small>Συνδέσου για να κάνεις προσφορά</small></p><br>
                        <%
                                } else if (currentUser.getVerified() == false) {
                        %>
                        <form method="post" action="" class="form-inline"><input type="number" class="form-control" name="bid">  <button type="submit" class="btn btn-danger" disabled="disabled">Κάνε προσφορά</button></form>
                        <p class="text-danger"><small>Αναμένεται η έγκριση του λογαριασμού σου από τον διαχειριστή για να μπορείς να συμμετέχεις σε δημοπρασίες</small></p><br>
                        <%
                                } else if (Objects.equals(currentUser.getUsername(), Item.getCurrent_buyer_username())) {
                        %>
                        <form method="post" action="" class="form-inline"><input type="number" class="form-control" name="bid">  <button type="submit" class="btn btn-danger" disabled="disabled">Κάνε προσφορά</button></form>
                        <p class="text-success"><small>Έχεις κάνει την μεγαλύτερη προσφορά μέχρι στιγμής (EUR <%=Item.getCurrently()%>)</small></p><br>
                        <%
                                } else if (Objects.equals(currentUser.getUsername(), Item.getSeller_name())) {
                        %>
                        <form method="post" action="" class="form-inline"><input type="number" class="form-control" name="bid">  <button type="submit" class="btn btn-danger" disabled="disabled">Κάνε προσφορά</button></form>    
                        <p><small>Είσαι ο κάτοχος της δημοπρασίας</small></p>
                        <%
                                } else {
                        %>
                        <form method="post" action="BidServlet" class="form-inline"><input type="number" class="form-control" name="bid">  <button type="submit" class="btn btn-danger">Κάνε προσφορά</button></form>
                        <p><small>EUR <%=Item.getCurrently()%> ή περισσότερα</small></p><br>
                        <%
                                }
                            } else {
                        %>
                        <p>Τελική προσφορά: <strong>EUR <%=Item.getCurrently()%></strong> <small>[<a href="DisplayBidsHistoryServlet?itemID=<%=Item.getItemID()%>"  onclick="window.open('DisplayBidsHistoryServlet?itemID=<%=Item.getItemID()%>', 'newwindow', 'width=770, height=400'); return false;"><%=Item.getNum_bids()%> προσφορές</a>]</small></p>
                        <%
                            }
                        %>
                        <p>Τοποθεσία αντικειμένου: <strong><%=Item.getCity()%>, <%=Item.getCountry()%></strong></p>
                        <p>Πωλητής: <strong><a href="DisplayUserServlet?username=<%=Item.getSeller_name()%>"><%=Item.getSeller_name()%></a></strong> <%if (!Objects.equals(currentUser.getUsername(), Item.getSeller_name())) {%><a href="sendmessage.jsp"  onclick="window.open('sendmessage.jsp', 'newwindow', 'width=770, height=350'); return false;"><i class="fa fa-envelope" aria-hidden="true"></i></a><%}%></p>
                    </div>
                </div>
                <div class="row">
                    <h3 align="center">Περιγραφή</h3><hr>
                    <p><%=Item.getDescription()%></p>
                </div>
                <div class="row">
                    <h3 align="center">Τοποθεσία</h3><hr>
                    <div id="mapdiv"></div>
                </div>
            </div>
        </section>

        <!-- jQuery -->
        <script src="vendor/jquery/jquery.min.js"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="vendor/bootstrap/js/bootstrap.min.js"></script>

        <!-- Plugin JavaScript -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
        <script src="vendor/scrollreveal/scrollreveal.min.js"></script>
        <script src="vendor/magnific-popup/jquery.magnific-popup.min.js"></script>

        <!-- Theme JavaScript -->
        <script src="js/creative.min.js"></script>
        
        <!-- Elevate Zoom JavaScript -->
        <script src="js/jquery.elevateZoom-3.0.8.min.js"></script>
        <script>
            $("#image").elevateZoom();
        </script>

    </body>

</html>
