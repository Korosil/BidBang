<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Γίνε μέλος | BidBang.gr</title>

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
                    <p class="navbar-text">
                        <a href="#" data-toggle="modal" data-target="#login-modal">Σύνδεση</a> | 
                        <a href="signup.jsp">Γίνε μέλος</a>
                    </p>
                </div>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>

    <body>
        <section class="bg-info">
            <div class="container">
                <div class="row">
                    <div class="col-md-4 col-md-offset-4">
                        <h1 align="center">Γίνε μέλος</h1><hr>
                        <form name="signup" method="post" action="SignupServlet" onsubmit="return validateForm()">
                            <div class="form-group">
                                <label>Όνομα</label>
                                <input type="text" class="form-control" name="FirstName" pattern="[A-Za-zΑ-Ωα-ω]{}" minlength="1" maxlength="45" placeholder="Όνομα"><br>
                                <input type="text" class="form-control" name="LastName" required pattern="[A-Za-zΑ-Ωα-ω]{}" minlength="1" maxlength="45" placeholder="Επώνυμο">
                            </div>
                            <div class="form-group">
                                <label>Διεύθυνση ηλεκτρονικού ταχυδρομείου</label>
                                <input type="email" class="form-control" name="Email" required pattern="[A-Za-z]{}">
                            </div>
                            <div class="form-group">
                                <label>Τηλέφωνο</label>
                                <input type="text" class="form-control" name="PhoneNumber" required pattern="[0-9]{}" placeholder="Τηλέφωνο">
                            </div>
                            <div class="form-group">
                                <label>Διεύθυνση</label>
                                <input type="text" class="form-control" name="Street" required  pattern="[A-Za-zΑ-Ωα-ω0-9]{}" minlength="1" maxlength="45" placeholder="Οδός"><br>
                                <input type="text" class="form-control" name="City" required pattern="[A-Za-zΑ-Ωα-ω]{}" minlength="1" maxlength="45" placeholder="Πόλη"><br>
                                <input type="text" class="form-control" name="State" required pattern="[A-Za-zΑ-Ωα-ω]{}" minlength="1" maxlength="45" placeholder="Νομός"><br>
                                <input type="text" class="form-control" name="Country" required pattern="[A-Za-zΑ-Ωα-ω]{}" minlength="1" maxlength="45" placeholder="Χώρα"><br>
                                <input type="text" class="form-control" name="ZipCode" required pattern="[0-9]{}" minlength="1" maxlength="45" placeholder="Ταχυδρομικός κώδικας">
                            </div>
                            <div class="form-group">
                                <label>Α.Φ.Μ.</label>
                                <input type="text" class="form-control" name="TRN" required pattern="[0-9]{}" minlength="1" maxlength="45">
                            </div>
                            <div class="form-group">
                                <label>Επιλέξτε το όνομα χρήστη σας</label>
                                <input type="text" class="form-control" name="Username" required pattern="[A-Za-z0-9]{}" minlength="1" maxlength="45">
                            </div>
                            <div class="form-group">
                                <label>Δημιουργία συνθηματικού</label>
                                <input type="password" class="form-control" name="Password">
                            </div>
                            <div class="form-group">
                                <label>Επιβεβαιώστε το συνθηματικό σας</label>
                                <input type="password" class="form-control" name="RPassword">
                            </div><br>
                            <button type="submit" class="btn btn-primary btn-block">Εγγραφή</button>
                        </form>
                    </div>
                </div>
            </div>
        </section>

        <script type="text/javascript">
        function validateForm() {
                
            var pass1 = document.forms["signup"]["Password"].value;
            var pass2 = document.forms["signup"]["RPassword"].value;
            
            if (pass1 === null || pass1 === "" || pass2 === null || pass2 === "") {
                alert("Ελέγξτε το συνθηματικό σας");
                return false;
            }
            if (pass1 !== pass2) {
                alert("Οι κωδικοί που εισάγετε πρέπει να είναι ίδιοι");
                return false;
            }
            
            return true;
        }
            

        </script>

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