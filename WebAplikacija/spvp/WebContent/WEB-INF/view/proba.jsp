<%@ page import="com.spvp.model.Prognoza" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.ArrayList" %>

<% ArrayList<Prognoza> lista = (ArrayList<Prognoza>)  request.getAttribute("prognoza"); %>

<%! String ispisPrognozu(ArrayList<Prognoza> lista) {
		
		int vel = lista.size();

		String ispis = "";

		for(int i=0; i<vel; i++)
		{
			ispis += "<br><br>"+ "Datum: " + lista.get(i).getDatum()+ " Vrijeme: " + lista.get(i).getVrijeme() + " Temperatura: " + lista.get(i).getTemperatura() + " Pritisak zraka: " + lista.get(i).getPritisakZraka() + " Vlaznost zraka: " + lista.get(i).getVlaznostZraka() + " Brzina vjetra: " + lista.get(i).getBrzinaVjetra();
		}

		return ispis;

}

%>
<%@page contentType="text/html" pageEncoding="windows-1250"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1250">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%= ispisPrognozu(lista) %>
    </body>
</html>
