<%@ page import="com.spvp.model.Prognoza" %>
<%@ page import="java.util.Date" %>
<% Prognoza prog = (Prognoza)  request.getAttribute("prognoza"); %>
<%!  String pisiZaDatum(Prognoza prog)
    {
        if(prog != null)
        {
            return new Date().toString();
        } 
        else
            return "";
    }
%>

<%!  String pisiZaGrad(Prognoza prog)
    {
        if(prog != null)
        {
            return prog.getZaGrad().getImeGrada();
        } 
        else
            return "";
    }
    %>

<%!  String ispisiVrijeme(Prognoza prog)
    {
        if(prog != null)
        {
            
            String vrijeme = "<p>Vrijeme: " + prog.getVrijeme() + "</p>";
                   vrijeme += "<p>Temperatura: " + prog.getTemperatura() + "&deg;C</p>";
                    vrijeme +=  "<p>Pritisak zraka: " + prog.getPritisakZraka() + " hPa</p>";
                    vrijeme +=  "<p>Vlaznost zraka: " + prog.getVlaznostZraka() + " %</p>";
                    vrijeme +=  "<p>Brzina vjetra: " + prog.getBrzinaVjetra() + " km/h</p>";

            return vrijeme;
        } 
        else
            return "";
    }
%>

<%!  String dajURLIkone(Prognoza prog){
        if(prog != null)
        {
            return prog.getWeatherIcon();
        } 
        else
            return "";
    }
%>
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
                        <li class="active"><a href="/spvp">Vrijeme danas</a></li>
                        <li><a href="/spvp/narednatridana">Vrijeme naredna tri dana</a></li>
                        <li><a href="/spvp/vrijemeubih">Vrijeme u BiH</a></li>
                        <li><a href="/spvp/vrijemenarednidanibih">Vrijeme naredna tri dana u BiH</a></li>
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

                <img id="backgroundImage" src="resources/images/background.jpg">
                        

                <div class="container topmargin">

                    <div class="row centered topmargin">
                        
                            <h3 class="headerText">Welcome! Weather forecasting by Omar and Ragib!</h3>
                        
                    </div>

                    <div class="row">
                        <div class="col-lg-12 centered">
                            <form id="mainForm" class="form-inline">
                                <input id="mainInputField" type="text" class="form-control cityInput" placeholder="Unesite naziv grada" />
                                <button class="btn btn-default">Trazi</button> 
                            </form>
                        </div>

                    </div>

                    <div class="row weathermargin robotoSlab">
                        <div class="col-lg-12">
                            <p><span id="vrijemeZaGrad"><%= pisiZaGrad(prog) %></span>, Bosna i Hercegovina <%= pisiZaDatum(prog) %></p>
                        </div>

                    </div>

                    <div class="row robotoSlab">

                        <div class="col-lg-2">
                            <img id="weatherImage" src='<%= dajURLIkone(prog) %>'>
                        </div>

                        <div id="izvjestajOVremenu" class="col-lg-3 weathermargin">
                            <%= ispisiVrijeme(prog) %>          
                        </div>

                        <div class="col-lg-1">
                            
                        </div>

                        <div id="map" class="col-lg-6">
                        
                        </div>

                    </div>
                
                </div>

                <footer class="footer">
                  <div class="container-fluid centered">   
                    <p class="text-muted textmargin">Copyright &copy 2016 Omar and Ragib</p>
                  </div>
                </footer>

            

        	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
            <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAjnQDa9sdWodJ98wH9VRV0E3efneFYv64"></script>
            <script src="resources/scripts/mainScript.js"></script>

        </body>

</html>