<!DOCTYPE html>
<html>

    <head>

        <meta charset="UTF-8">

        <title>Weather Forecasting By Omar And Ragib</title>

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

        <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">

        <link href="https://fonts.googleapis.com/css?family=Roboto+Slab" rel="stylesheet">

        <link href="https://fonts.googleapis.com/css?family=Oswald" rel="stylesheet"> 

        <link rel="stylesheet" href="resources/css/custom.css">

    </head>

        <body>

                <nav class="navbar navbar-default navbar-fixed-top">
                  <div class="container-fluid">
                    <!-- Brand and toggle get grouped for better mobile display -->
                    <div class="navbar-header">
                      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                      </button>
                    </div>

                    <!-- Collect the nav links, forms, and other content for toggling -->
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                      <ul class="nav navbar-nav">
                        <li><a href="/spvp">Vrijeme danas</a></li>
                        <li><a href="/spvp/narednatridana">Vrijeme naredna tri dana</a></li>
                        <li><a href="/spvp/vrijemeubih">Vrijeme u BiH</a></li>
                        <li class="active"><a href="/spvp/vrijemenarednidanibih">Vrijeme naredna tri dana u BiH</a></li>
                      </ul>
                      <form id="navForm" class="form-inline navbar-form navbar-right">
                        <div class="form-group">
                          <input id="navInputField" type="text" name="city" class="form-control" placeholder="Unesite naziv grada" />
                        </div>
                        <button type="submit" class="btn btn-default">Trazi</button>
                      </form>

                    </div><!-- /.navbar-collapse -->
                  </div><!-- /.container-fluid -->
                </nav>

                <div class="cleaner">
                    
                </div>


                    <div class="row robotoSlab">

                        <div id="mapabih" class="col-lg-12">
                        
                        </div>

                    </div>


                
                

                <footer class="footer">
                  <div id="footer" class="container-fluid centered">   
                    <p class="text-muted textmargin">Copyright &copy 2016 Omar and Ragib</p>
                  </div>
                </footer>

            

        	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
            <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAjnQDa9sdWodJ98wH9VRV0E3efneFYv64"></script>
            <script src="resources/scripts/vrijemeubihnarednidaniScript.js"></script>

        </body>

</html>

