<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="Beans.UserBean" import="Beans.DisplayAuctionsBean" import="java.util.Objects" import="java.util.ArrayList"%>

<!DOCTYPE html>

<html>

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Διαχείριση δημοπρασιών | BidBang.gr</title>

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
                    <div style="display: inline-block; padding: 17px">
                        <% UserBean currentUser = (UserBean) (session.getAttribute("currentSessionUser")); %>
                        <span>Καλωσόρισες </span><span class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><%=currentUser.getUsername()%> <span class="caret"></span></a>
                            <ul class="dropdown-menu pull-right">
                                <li><a href="DisplayAuctionsServlet">Διαχείριση δημοπρασιών</a></li>
                                <li><a href="addauction.jsp">Προσθήκη δημοπρασίας</a></li>
                                <% int unread = (Integer) session.getAttribute("unread");%>
                                <li><a href="MessageServlet?user=<%=currentUser.getUsername()%>">Μηνύματα <%if(unread > 0){%><span class="badge"><%=unread%></span><%}%></a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="LogoutServlet">Αποσύνδεση</a></li>
                            </ul>
                        </span>
                    </div>

                </div>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>

    <body>
        <section class="bg-info">
            <div class="container">
                <div class="row">
                    <div class="col-md-10 col-md-offset-1">
                        <%
                            DisplayAuctionsBean AuctionsBean = (DisplayAuctionsBean) session.getAttribute("auctions");
                            ArrayList SellerAuctions = AuctionsBean.getSeller_auctions();
                        %>
                        <h3 align="center">Οι δημοπρασίες μου</h3><hr>
                        <%
                            if (!SellerAuctions.isEmpty()) {
                        %>
                        <table id="myauctions" class="table table-responsive" style="table-layout: auto">
                            <thead>
                                <tr>
                                    <th>Αντικείμενο</th>
                                    <th>Τελευταία προσφορά</th>
                                    <th>Τρέχων αγοραστής</th>
                                    <th>Ημ/νία λήξης</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <%
                                for (int i = 0; i < SellerAuctions.size(); i++) {
                                    ArrayList auctionInfo = (ArrayList) SellerAuctions.get(i);
                            %>
                            <tr>
                                <th><a href="DisplayItemServlet?itemID=<%=auctionInfo.get(0)%>"><%=auctionInfo.get(1)%></a></th>
                                <th><%=auctionInfo.get(2)%> €</th>
                                <% if (auctionInfo.get(3) != null) { %>
                                <th><a href="DisplayUserServlet?username=<%=auctionInfo.get(3)%>"><%=auctionInfo.get(3)%></th>
                                <% } else { %>
                                <th>Κανένας</th>
                                <% } %>
                                <th><%=auctionInfo.get(4)%></th>
                                <th><a class="btn btn-danger" href="DeleteAuctionServlet?itemID=<%=auctionInfo.get(0)%>" role="button" <%if(auctionInfo.get(3)!=null){%>disabled="disabled"<%}%>>Διαγραφή</a></th>
                            </tr>
                            <%
                                }
                            %>
                        </table>
                        <%
                            } else {
                        %>
                        <p style="text-align: center; color: #bbb">Δεν υπάρχουν δημοπρασίες</p>
                        <%
                            }
                        %>
                        <h3 align="center">Οι δημοπρασίες που συμμετέχω</h3><hr>
                        <%
                            ArrayList Auctions = AuctionsBean.getAuctions_array();
                            if (!Auctions.isEmpty()) {
                        %>
                        <table id="auctions" class="table table-responsive" style="table-layout: auto">
                            <thead>
                                <tr>
                                    <th>Αντικείμενο</th>
                                    <th>Τελευταία προσφορά</th>
                                    <th>Τρέχων αγοραστής</th>
                                    <th>Ημ/νία λήξης</th>
                                </tr>
                            </thead>
                            <%
                                for (int i = 0; i < Auctions.size(); i++) {
                                    ArrayList auctionInfo = (ArrayList) Auctions.get(i);
                                    if (Objects.equals(auctionInfo.get(3), currentUser.getUsername())) {
                            %>
                            <tr class="success">
                                <%
                                    } else {
                                %>
                            <tr class="danger">
                                <%
                                    }
                                %>
                                <th><a href="DisplayItemServlet?itemID=<%=auctionInfo.get(0)%>"><%=auctionInfo.get(1)%></a></th>
                                <th><%=auctionInfo.get(2)%> €</th>
                                <th><a href="DisplayUserServlet?username=<%=auctionInfo.get(3)%>"><%=auctionInfo.get(3)%></th>
                                <th><%=auctionInfo.get(4)%></th>
                            </tr>
                            <%
                                }
                            %>
                        </table>
                        <%
                            } else {
                        %>
                        <p style="text-align: center; color: #bbb">Δεν υπάρχουν δημοπρασίες</p>
                        <%
                            }
                        %>
                    </div>
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
            
            $('#myauctions').dataTable({
                "searching": false,
                //"lengthChange": false,
                "columnDefs": [
                    { "orderable": false, "targets": [1,2,4]}
                ],
                "order": [[3, 'asc']],
                "aoColumns": [
			null,
			null,
                        { "sType": "numeric-comma" },
			null,
                        null
		],   
                stateSave: true
            });
            
            $('#auctions').dataTable({
                "searching": false,
                //"lengthChange": false,
                "columnDefs": [
                    { "orderable": false, "targets": [1,2]}
                ],
                "order": [[3, 'asc']],
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
