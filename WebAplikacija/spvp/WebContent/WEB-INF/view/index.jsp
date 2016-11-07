<%@ page import="com.spvp.model.Prognoza" %>
<%@ page import="java.util.Date" %>
<% Prognoza prog = (Prognoza)  request.getAttribute("prognoza"); %>
<!DOCTYPE html>
<html>

    <head>

        <meta charset="UTF-8">

        <title>Weather Forecasting By Omar And Ragib</title>

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

        <link rel="stylesheet" href="resources/css/custom.css">

    </head>

        <body>
            <div class="container">
                <div class="row">
                    <ul class="nav nav-tabs">
                      <li class="nav-item col-lg-3">
                        <a class="nav-link active" href="/spvp">Vrijeme danas</a>
                      </li>
                      <li class="nav-item col-lg-3">
                        <a class="nav-link" href="/spvp/welcome">Vrijeme u naredna 2 dana</a>
                      </li>
                      <li class="nav-item col-lg-3">
                        <a class="nav-link" href="#">Vrijeme danas u BiH</a>
                      </li>
                      <li class="nav-item col-lg-3">
                        <a class="nav-link" href="#">Vrijeme u BiH naredna 2 dana</a>
                      </li>
                    </ul>
                </div> 

                <div class="row">
                    <div class="col-lg-12">
                        <img src="resources/images/headerpic.jpg" class="img-fluid img-thumbnail">
                    </div>
                    
                </div> 

                <div class="row">
                    <div class="col-lg-12 centered">
                        <form action="/spvp/" method="POST" class="form-inline">
                            <input type="text" name="city" class="form-control" placeholder="Unesite naziv grada" />
                            <button type="submit" class="btn btn-default">Trazi</button> 
                        </form>
                    </div>

                </div>

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
                                    vrijeme +=  "<p>Pritisak zraka: " + prog.getPritisakZraka() + " hPa</p>";
                                    vrijeme +=  "<p>Vlaznost zraka: " + prog.getVlaznostZraka() + " %</p>";
                                    vrijeme +=  "<p>Brzina vjetra: " + prog.getBrzinaVjetra() + " km/h</p>";

                            return vrijeme;
                        } 
                        else
                            return "";
                    }
                %>

                <%!  String dajURLIkone(Prognoza prog)
                    {
                        if(prog != null)
                        {
                            return "";
                        } 
                        else
                            return "";
                    }
                %>

                <div class="row topmargin">
                    <div class="col-lg-12">
                        <p>Prikaz vremena za <%= pisiZaGrad(prog) %>, Bosna i Hercegovina <%= pisiZaDatum(prog) %></p>
                    </div>

                </div>

                <div class="row topmargin">

                    <div class="col-lg-3">
                        <img src='resources/images/clear.jpg'>
                    </div>

                    <div class="col-lg-3">
                        <%= ispisiVrijeme(prog) %>          
                    </div>

                </div>

                
            
            </div>

        	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        </body>

</html>