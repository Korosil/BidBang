<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="Beans.UnverifiedUsersBean" import="java.util.Objects" import="java.util.ArrayList"%>

<!DOCTYPE html>

<html>

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <%
            UnverifiedUsersBean UnverifiedBean = (UnverifiedUsersBean) session.getAttribute("UnverifiedUsers");
            ArrayList<String> UnverifiedUsers = null;
            if (UnverifiedBean != null) {
                UnverifiedUsers = UnverifiedBean.getUnverifiedUsers_rs();
            }
        %>

        <title>Administration panel | BidBang.gr</title>

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
                    <a class="navbar-brand" style="color: #f68a22" href="index.jsp">BidBang</a>
                    <p class="navbar-text"><strong>Administration Panel</strong></p>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </nav>

    <body>
        <section class="bg-info">
            <div class="container">
                <div class="row">
                    <h2 align="center">Διαχείριση χρηστών</h2>
                    <%
                        if (UnverifiedUsers != null) {
                            if (!UnverifiedUsers.isEmpty()) {
                    %>
                        <form method="post" action="VerifyUsersServlet">
                            <table id="users" class="table table-responsive" style="table-layout: auto">
                                <thead>
                                    <tr>
                                        <th>Όνομα χρήστη</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <%
                                    for (int i = 0; i < UnverifiedUsers.size(); i++) {
                                %>
                                <tr>
                                    <th style="vertical-align:middle"><a href="DisplayUserServlet?username=<%=UnverifiedUsers.get(i)%>"><%=UnverifiedUsers.get(i)%></a></th>
                                    <th style="vertical-align:middle; text-align: right"><input type="checkbox" name="UnverifiedUsers" value="<%=UnverifiedUsers.get(i)%>"></th>
                                </tr>
                                <%
                                    }
                                %>
                            </table><br>
                            <p align="center">Επιλογή όλων <input type="checkbox" onclick="checkAll(this)"></p>
                            <p align="center"><button type="submit" class="btn btn-primary btn-xl">Έγκριση</button></p>
                        </form>
                    <%
                            } else {
                    %>
                    <h3 align="center" style="color: #bbb"><i class="fa fa-check-circle-o fa-4x" aria-hidden="true"></i></h3>
                    <h3 align="center" style="color: #bbb">Όλοι οι χρήστες έχουν εγκριθεί</h3>
                    <%
                            }
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
            $('#users').dataTable({
                "searching": false,
                //"lengthChange": false,
                "columnDefs": [
                    {"orderable": false, "targets": [1]}
                ],
                "order": [[0, 'asc']],
                stateSave: true
            });
        </script>
        
        <!-- Check All JavaScript -->
        <script>
            function checkAll(bx) {
                var cbs = document.getElementsByTagName('input');
                for(var i=0; i < cbs.length; i++) {
                    if(cbs[i].type == 'checkbox') {
                        cbs[i].checked = bx.checked;
                    }
                }
            }
        </script>
    </body>

</html>
