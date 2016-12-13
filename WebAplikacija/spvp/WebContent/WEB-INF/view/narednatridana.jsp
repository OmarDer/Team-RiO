<%@ page import="com.spvp.model.Prognoza" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.TimeZone" %>
<%@ page import="java.util.ArrayList" %>
<% ArrayList prog = (ArrayList) request.getAttribute("prognoza"); %>
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

<%!  String vratiDan(int brojDanaUnaprijed)
    {
       Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

       calendar.add(Calendar.DATE, brojDanaUnaprijed);

       int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

       switch(dayOfWeek){
        case 1: return "Ponedjeljak";
        case 2: return "Utorak";
        case 3: return "Srijeda";
        case 4: return "Cetvrtak";
        case 5: return "Petak";
        case 6: return "Subota";
        case 7: return "Nedjelja";
       }

       return "";
    }
%>

<%!  String pisiZaGrad(Prognoza prog)
    {
        if(prog != null)
        {
            return prog.getZaGrad();
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

        <title>Weather Forecast In Next 3 Days By Omar And Ragib</title>

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
                        <li class="active"><a href="/spvp/narednatridana">Vrijeme naredna tri dana</a></li>
                        <li><a href="/spvp/vrijemeubih">Vrijeme u BiH</a></li>
                        <li><a href="#">Vrijeme naredna tri dana u BiH</a></li>
                      </ul>
                      <form id="navFormNaredniDani" class="form-inline navbar-form navbar-right">
                        <div class="form-group">
                          <input id="navInputFieldNaredniDani" type="text" name="city" class="form-control" placeholder="Unesite naziv grada" />
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
                        
                            <h3 class="headerText">Weather forecast in next 3 days by Omar and Ragib!</h3>
                        
                    </div>

                    <div class="row">
                        <div class="col-lg-12 centered">
                            <form id="mainFormNaredniDani" class="form-inline">
                                <input id="mainInputFieldNaredniDani" type="text" class="form-control cityInput" placeholder="Unesite naziv grada" />
                                <button class="btn btn-default">Trazi</button> 
                            </form>
                        </div>

                    </div>

                    
                    <div class="row weathermargin robotoSlab">
                        <div class="col-lg-12">
                            <p><span id="vrijemeZaGrad"><%= pisiZaGrad((Prognoza)prog.get(0)) %></span>, Bosna i Hercegovina <%= pisiZaDatum((Prognoza)prog.get(0)) %></p>
                        </div>

                    </div>

                    <div class="row robotoSlab">

                        <div class="col-lg-4">
                            <div class="row centered">
                                <h3 class="centered">Sutra</h3>
                            </div>
                            <img id="weatherImageDan1" src='<%= dajURLIkone((Prognoza)prog.get(0)) %>'>

                            <div id="izvjestajOVremenuDan1" class="col-lg-3 weathermargin">
                                <%= ispisiVrijeme((Prognoza)prog.get(0)) %>          
                            </div>
                        </div>

                        <div class="col-lg-4">
                            <div class="row centered">
                                <h3 class="centered"><%= vratiDan(1) %></h3>
                            </div>
                            <img id="weatherImageDan2" src='<%= dajURLIkone((Prognoza)prog.get(1)) %>'>

                            <div id="izvjestajOVremenuDan2" class="col-lg-3 weathermargin">
                                <%= ispisiVrijeme((Prognoza)prog.get(1)) %>          
                            </div>
                        
                        </div>

                        <div class="col-lg-4">
                            <div class="row centered">
                                <h3 class="centered"><%= vratiDan(2) %></h3>
                            </div>
                            <img id="weatherImageDan3" src='<%= dajURLIkone((Prognoza)prog.get(2)) %>'>


                            <div id="izvjestajOVremenuDan3" class="col-lg-3 weathermargin">
                                <%= ispisiVrijeme((Prognoza)prog.get(2)) %>          
                            </div>
                        
                        </div>

                    </div>

                    <div class="row">

                        <div class="col-lg-3">
                        
                        </div>

                        <div id="map" class="col-lg-8">
                        
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