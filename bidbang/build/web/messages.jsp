<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="Beans.UserBean" import="Beans.MessageBean" import="java.util.ArrayList"%>

<!DOCTYPE html>

<html>

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Μηνύματα | BidBang.gr</title>

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
                            MessageBean MBean = (MessageBean) session.getAttribute("messages");
                            ArrayList inmessages = MBean.getIn_messages();
                        %>
                        <h3 align="center">Εισερχόμενα</h3><hr>
                        <%
                            if (!inmessages.isEmpty()) {
                        %>
                        <table id="inmessages" class="table table-responsive" style="table-layout: auto">
                            <%
                                for (int i = 0; i < inmessages.size(); i++) {
                                    ArrayList inmessagesInfo = (ArrayList) inmessages.get(i);
                            %>
                            <tr>
                                <th <%if((Boolean)inmessagesInfo.get(0)==true){%> style="font-weight:normal"<%}%>><%=inmessagesInfo.get(1)%></th>
                                <th style="width:70%"><a href="MessageSeenServlet?messageID=<%=inmessagesInfo.get(5)%>"><preview <%if((Boolean)inmessagesInfo.get(0)==true){%> style="font-weight:normal"<%}%>><%=inmessagesInfo.get(2)%></preview></a></th>
                                <th <%if((Boolean)inmessagesInfo.get(0)==true){%> style="font-weight:normal"<%}%>><%=inmessagesInfo.get(3)%> <%=inmessagesInfo.get(4)%></th>
                            </tr>
                            <%
                                }
                            %>
                        </table>
                        <%
                            } else {
                        %>
                        <p style="text-align: center; color: #bbb">Δεν υπάρχουν εισερχόμενα μηνύματα</p>
                        <%
                            }
                        %>
                        <h3 align="center">Απεσταλμένα</h3><hr>
                        <%
                            ArrayList outmessages = MBean.getOut_messages();
                            if (!outmessages.isEmpty()) {
                        %>
                        <table id="outmessages" class="table table-responsive" style="table-layout: auto">
                            <%
                                for (int i = 0; i < outmessages.size(); i++) {
                                    ArrayList outmessagesInfo = (ArrayList) outmessages.get(i);
                            %>
                            <tr>
                                <th style="font-weight: normal"><%=outmessagesInfo.get(0)%></th>
                                <th style="width: 70%; font-weight: normal"><preview><%=outmessagesInfo.get(1)%></preview></th>
                                <th style="font-weight: normal"><%=outmessagesInfo.get(2)%> <%=outmessagesInfo.get(3)%></th>
                            </tr>
                            <%
                                }
                            %>
                        </table>
                        <%
                            } else {
                        %>
                        <p style="text-align: center; color: #bbb">Δεν υπάρχουν απεσταλμένα μηνύματα</p>
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
        <script type="text/javascript" charset="utf-8" src="//cdn.datatables.net/1.10.12/js/jquery.dataTables.js"></script>
        <script type="text/javascript">
            jQuery.extend( jQuery.fn.dataTableExt.oSort, {
            "datetime-asc": function ( a, b ) {
                var x, y;
                if (jQuery.trim(a) !== '') {
                    var Datea = jQuery.trim(a).split(' ');
                    var Timea = Datea[1].split(':');
                    var Datea2 = Datea[0].split('-');
                    x = (Datea2[0] + Datea2[1] + Datea2[1] + Timea[0] + Timea[1] + Timea[2]) * 1;
                } else {
                    x = Infinity; // = l'an 1000 ...
                }
 
                if (jQuery.trim(b) !== '') {
                 var Dateb = jQuery.trim(b).split(' ');
                    var Timeb = Dateb[1].split(':');
                    Dateb = Dateb[0].split('-');
                    y = (Dateb[0] + Dateb[1] + Dateb[2] + Timeb[0] + Timeb[1] + Timeb[2]) * 1;
                } else {
                    y = Infinity;
                }
                var z = ((x < y) ? -1 : ((x > y) ? 1 : 0));
                return z;
            },
 
            "datetime-desc": function ( a, b ) {
                var x, y;
                if (jQuery.trim(a) !== '') {
                    var Datea = jQuery.trim(a).split(' ');
                    var Timea = Datea[1].split(':');
                    var Datea2 = Datea[0].split('-');
                    x = (Datea2[0] + Datea2[1] + Datea2[2] + Timea[0] + Timea[1] + Timea[2]) * 1;
                } else {
                    x = Infinity;
                }
 
                if (jQuery.trim(b) !== '') {
                    var Dateb = jQuery.trim(b).split(' ');
                    var Timeb = Dateb[1].split(':');
                    Dateb = Dateb[0].split('-');
                    y = (Dateb[0] + Dateb[1] + Dateb[2] + Timeb[0] + Timeb[1] + Timeb[2]) * 1;
                } else {
                    y = Infinity;
                }
                var z = ((x < y) ? 1 : ((x > y) ? -1 : 0));
                return z;
                }
            } );
        </script>
        <script>
            $('#inmessages').dataTable( {
                "searching": false,
                columnDefs: [
                { type: 'datetime', targets: 2 },
                { "orderable": false, "targets": [0,1,2]}
                ],
                "order": [[2, 'desc']],
                stateSave: true
            } );
            $('#outmessages').dataTable( {
                "searching": false,
                columnDefs: [
                { type: 'datetime', targets: 2 },
                { "orderable": false, "targets": [0,1,2]}
                ],
                "order": [[2, 'desc']],
                stateSave: true
            } );
        </script>
        
    </body>

</html>
