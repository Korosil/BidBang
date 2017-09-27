<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="Beans.UserBean" import="Beans.SearchBean" import="java.util.ArrayList" import="java.util.Objects"%>

<!DOCTYPE html>

<html>

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <%
            SearchBean CurrentSearch = (SearchBean) (session.getAttribute("CurrentSearch"));
            ArrayList results = CurrentSearch.getSearch_rs();
        %>

        <title>Αποτελέσματα για <%=CurrentSearch.getSearch_item()%> | BidBang.gr</title>

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
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" <%if(currentUser.getVerified() == false){%>style="color:#9d9d9d"<%}%>><%=currentUser.getUsername()%> <span class="caret"></span></a>
                            <ul class="dropdown-menu pull-right">
                                <% if (Objects.equals(currentUser.getUsername(), "admin")) { %>
                                <li><a href="UnverifiedUsersServlet">Διαχείριση χρηστών</a></li>
                                
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
                    <% } %>

                </div>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>

    <body>
        <section class="bg-info">
            <div class="container">
                <div class="row">
                    <h3 align="center">Αποτελέσματα για <strong><%=CurrentSearch.getSearch_item()%></strong></h3>
                    <hr>
                    <%
                        if (!results.isEmpty()) {
                    %>
                    <table id="items" class="table table-striped" style="table-layout: auto">
                        <thead>
                            <tr>
                                <th></th>
                                <th>Αντικείμενο</th>
                                <th>Τιμή</th>
                                <th>Χώρα</th>
                            </tr>
                        </thead>
                        <%
                            for (int i = 0; i < results.size(); i++) {
                                ArrayList results2 = (ArrayList) results.get(i);
                        %>
                        <tr>
                            <th style="width: 15%"><a href="DisplayItemServlet?itemID=<%=results2.get(0)%>"><img src="${pageContext.request.contextPath}/images/<%=results2.get(0)%>" class="img-thumbnail" style="object-fit: contain; width: 150px; height: 150px;"></a></th>
                            <th style="width: 50%"><a href="DisplayItemServlet?itemID=<%=results2.get(0)%>"><%=results2.get(1)%></a></th>
                            <th style="font-size: x-large"><%=results2.get(2)%> €</th>
                            <th><%=results2.get(3)%></th>
                        </tr>
                        <%
                            }
                        %>
                    </table>
                    <%} else {
                    %>
                    <h5 align="center">Δεν βρέθηκαν αποτελέσματα</h5>
                    <%
                        }
                    %>

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
        
        <!-- DataTables JavaScript -->
        <script src="//code.jquery.com/jquery-1.12.3.js"></script>
        <script src="js/jquery.dataTables.min.js"></script>
        <script>
            jQuery.fn.dataTableExt.oSort['numeric-comma-asc']  = function(a,b) {
                var x = (a == "-") ? 0 : a.replace( /,/, "." );
                var y = (b == "-") ? 0 : b.replace( /,/, "." );
                x = parseFloat( x );
                y = parseFloat( y );
                return ((x < y) ? -1 : ((x > y) ?  1 : 0));
            };

            jQuery.fn.dataTableExt.oSort['numeric-comma-desc'] = function(a,b) {
                var x = (a == "-") ? 0 : a.replace( /,/, "." );
                var y = (b == "-") ? 0 : b.replace( /,/, "." );
                x = parseFloat( x );
                y = parseFloat( y );
                return ((x < y) ?  1 : ((x > y) ? -1 : 0));
            };
            
            $('#items').dataTable({
                "searching": false,
                //"lengthChange": false,
                "columnDefs": [
                    { "orderable": false, "targets": [0,1,3]}
                ],
                "order": [[2, 'asc']],
                "aoColumns": [
			null,
			null,
			{ "sType": "numeric-comma" },
			null
		],   
                stateSave: true
            });
        </script>

    </body>

</html>
