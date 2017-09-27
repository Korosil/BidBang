<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="Beans.UserBean" import="Beans.RecommendationBean" import="java.util.Objects" import="java.util.ArrayList" import="java.util.Date"%>

<!DOCTYPE html>

<html>

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>BidBang.gr</title>

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

    <body>
        <jsp:include page="UnreadServlet" />
        <jsp:include page="RecommendationServlet" />
        <header>
            <div class="container">
                <div class="center-block">

                    <%
                        UserBean currentUser = (UserBean) (session.getAttribute("currentSessionUser"));
                        if (currentUser == null) {
                    %>
                    <p class="pull-right" style="padding: 40px">
                        <a href="#" data-toggle="modal" data-target="#login-modal">Σύνδεση</a> | 
                        <a href="./signup.jsp">Γίνε μέλος</a>
                    </p>
                    <%}
                        else {
                    %>
                    <div class="pull-right" style="padding: 40px; display: inline-block">
                        <span>Καλωσόρισες </span><span class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" <%if(currentUser.getVerified() == false){%>style="color:#ccc"<%}%>><%=currentUser.getUsername()%> <span class="caret"></span></a>
                            <ul class="dropdown-menu pull-right">
                                <% if (Objects.equals(currentUser.getUsername(), "admin")) { %>
                                <li><a href="UnverifiedUsersServlet">Διαχείριση χρηστών</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="LogoutServlet">Αποσύνδεση</a></li>
                                    <% } else if(currentUser.getVerified() == true){ %>
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
                    <%
                        }
                    %>

                </div>
            </div>
            <div class="header-content">
                <div class="header-content-inner">
                    <a href="index.jsp"><h1 style="color: #f68a22">BidBang</h1></a>
                    <br>
                    <form method="post" action="SearchServlet">
                        <div class="input-group input-group-lg">
                            <input type="search" name="searchterm" class="form-control" placeholder="γράψε τον όρο αναζήτησης">
                            <span class="input-group-btn">
                                <button class="btn btn-primary" type="submit">Αναζήτηση</button>
                            </span>
                        </div>
                    </form>
                    <br>
                    <p>Πλοηγηθείτε εύκολα και γρήγορα στις κατηγορίες των προϊόντων <strong><a href="DisplayCategoryServlet">εδώ</a></strong></p>
                </div>
            </div>
        </header>
           
        <%
            RecommendationBean RBean = new RecommendationBean();
            RBean = (RecommendationBean) request.getAttribute("rec_bean");
            if (RBean != null) {
        %>
        <section class="bg-info" id="about">
            <div class="container">
                <div class="row">
                    <h3 align="center">Προτεινόμενα αντικείμενα για σένα</h3><hr>
        <%
                ArrayList<Integer> ID = RBean.getRecommended_itemIDs();
                ArrayList<String> name = RBean.getRecommended_items();
                ArrayList<Float> cprice = RBean.getCurrently_prices();
                ArrayList<Date> edate = RBean.getEnd_dates();
                ArrayList<String> countries = RBean.getitemCountries();
                for (int i = 0; i < ID.size(); i++) {
        %>
                    <div class="col-md-2 <%if (i==0) {%>col-md-offset-1<%}%>">
                        <div class="thumbnail">
                            <a href="DisplayItemServlet?itemID=<%=ID.get(i)%>"><img src="${pageContext.request.contextPath}/images/<%=ID.get(i)%>"></a>
                            <div class="caption">
                                <a href="DisplayItemServlet?itemID=<%=ID.get(i)%>"><strong><%=name.get(i)%></strong></a>
                                <p style="font-size: x-large"><strong><%=cprice.get(i)%> €</strong></p>
<!--                                <p><%=edate.get(i)%></p>
                                <p><%=countries.get(i)%></p>-->
                            </div>
                        </div>
                    </div>
        <%
                    if (i == 5)
                        break;
                }
        %>
                </div>    
            </div>
        </section>
        <%
            }
        %>

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

    </body>

</html>
