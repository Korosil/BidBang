<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="Beans.UserBean"%>

<!DOCTYPE html>

<html>

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Προσθήκη δημοπρασίας | BidBang.gr</title>

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
                        <h3 align="center">Προσθήκη δημοπρασίας</h3><hr>
                        <form name="addauction" method="post" action="AddAuctionServlet" accept-charset="UTF-8" enctype="multipart/form-data">
                            <div class="form-group">
                                <label>Τίτλος αντικειμένου</label>
                                <input type="text" class="form-control" name="Name" pattern="[A-Za-zΑ-Ωα-ω]{}" minlength="1" maxlength="45" placeholder="π.χ. Samsung Galaxy S7 Edge"><br>
                            </div>
                            <div class="form-group">
                                <label>Κατηγορία</label>
                                <select name="Category">
                                    <option value="Smartphones">Smartphones</option>
                                    <option value="Laptops">Laptops</option>
                                    <option value="PC">Υπολογιστές</option>
                                    <option value="Accessories">Αξεσουάρ</option>
                                    <option value="TVs">Τηλεοράσεις</option>
                                    <option value="Tablets">Tablets</option>
                                    <option value="Video">Εικόνα</option>
                                    <option value="Audio">Ήχος</option>
                                    <option value="Home">Για το σπίτι</option>
                                    <option value="Clothing">Ρουχισμός</option>
                                    <option value="Automoto">Auto-Moto</option>
                                </select><br>
                            </div>
                            <div class="form-group">
                                <label>Τιμή εκκίνησης</label>
                                <input type="number" step="0.10" class="form-control" name="FirstBid"><br>
                            </div>
                            <div class="form-group">
                                <label>Τελική τιμή <small>(προαιρετικό πεδίο)</small></label>
                                <input type="number" step="0.10" class="form-control" name="BuyPrice"><br>
                                
                            </div>
                            <div class="form-group">
                                <label>Τοποθεσία αντικειμένου</label>
                                <input type="text" class="form-control" name="City" minlength="1" maxlength="45" placeholder="Πόλη"><br>
                                <input type="text" class="form-control" name="State" minlength="1" maxlength="45" placeholder="Νομός"><br>
                                <input type="text" class="form-control" name="Country" minlength="1" maxlength="45" placeholder="Χώρα"><br>
                            </div>
                            <div class="form-group">
                                <label>Περιγραφή αντικειμένου</label>
                                <textarea class="form-control" name="Description" minlength="1" maxlength="1000"></textarea><br>
                            </div>
                            <div class="form-group">
                                <label>Ημ/νία λήξης δημοπρασίας</label>
                                <input type="date" class="form-control" name="EndDate" placeholder="ΧΧΧΧ-ΜΜ-ΗΗ"><br>
                            </div>
                            <div class="form-group">
                                <label>Εικόνα</label>
                                <input type="file" name="photo">
                                <small>(< 65kb)</small><br>
                            </div>
                            <button type="submit" class="btn btn-primary btn-block">Καταχώρηση</button>
                        </form>
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
    
    </body>

</html>
