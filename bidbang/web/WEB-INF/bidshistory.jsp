<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="Beans.BidsHistoryBean" import="Beans.BidBean" import="java.util.ArrayList" import="java.util.Objects"%>

<!DOCTYPE html>

<html>

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Ιστορικό προσφορών</title>

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
    
    <body>
        <section class="bg-info">
            <div class="container">
                <div class="row">
                    <%
                        BidsHistoryBean Bids = (BidsHistoryBean) (session.getAttribute("History"));
                        ArrayList history = Bids.get_thebids();
                    %>
                    <table id="history" class="table table-responsive" style="table-layout: auto">
                        <thead>
                            <tr>
                                <th>Χρήστης</th>
                                <th>Ποσό</th>
                                <th>Ημ/νία</th>
                            </tr>
                        </thead>
                        <%
                            for (int i = 0; i < history.size(); i++) {
                                BidBean bidinfo = (BidBean) history.get(i);
                        %>
                        <tr>
                            <th><a href="DisplayUserServlet?username=<%=bidinfo.getUsername()%>"><%=bidinfo.getUsername()%></a></th>
                            <th><%=bidinfo.getAmount()%> €</th>
                            <th><%=bidinfo.getTime_of_bid()%></th>
                        </tr>
                        <%
                            }
                        %>
                    </table>
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
            
            $('#history').dataTable({
                "searching": false,
                //"lengthChange": false,
                "columnDefs": [
                    { "orderable": false, "targets": [0,1]}
                ],
                "order": [[2, 'desc']],
                "aoColumns": [
			null,
			null,
			{ "sType": "numeric-comma" }
		],   
                stateSave: true
            });
        </script>
        
    </body>
    
</html>
