$( document ).ready(function main(){
	$.ajax({
	  url: "http://ip-api.com/json",
	  success: initMap,
	  dataType:'json',
      type: 'get',
	});

	initializeEvents();
});

function initializeEvents(){
	$('#mainForm').submit(function(e) {
    	e.preventDefault();


    	var grad = $("#mainInputField").val();

    	var weatherURL = "http://api.worldweatheronline.com/premium/v1/weather.ashx?q=" + grad + ",ba&key=c868e1f7b5a24e97a44211932160711&fx=no&includelocation=yes&mca=no&format=json";

    	$.ajax({
		  url: weatherURL,
		  success: prikaziVrijeme,
		  dataType:'json',
	      type: 'get',
		});

    
	});

	$('#mainFormNaredniDani').submit(function(e) {
    	e.preventDefault();


    	var grad = $("#mainInputFieldNaredniDani").val();

    	var URL = "http://localhost:8084/spvp/prognozatridana/" + grad;

    	$.ajax({
		  url: URL,
		  success: prikaziVrijemeNarednaTriDana,
		  dataType:'json',
	      type: 'get',
		});

    
	});

	$('#navForm').submit(function(e) {
    	e.preventDefault();


    	var grad = $("#navInputField").val();

    	var weatherURL = "http://api.worldweatheronline.com/premium/v1/weather.ashx?q=" + grad + ",ba&key=c868e1f7b5a24e97a44211932160711&fx=no&includelocation=yes&mca=no&format=json";

    	$.ajax({
		  url: weatherURL,
		  success: prikaziVrijeme,
		  dataType:'json',
	      type: 'get',
		});

    
	});

	$('#navFormNaredniDani').submit(function(e) {
    	e.preventDefault();


    	var grad = $("#navInputFieldNaredniDani").val();

    	var URL = "http://localhost:8084/spvp/prognozatridana/" + grad;

    	$.ajax({
		  url: URL,
		  success: prikaziVrijemeNarednaTriDanaNav,
		  dataType:'json',
	      type: 'get',
		});

    
	});
}

function prikaziVrijemeNarednaTriDanaNav(data, status, jqXHR){
	if(status == "success"){
		var x = JSON.stringify(data);
		var obj = jQuery.parseJSON(x);
		//alert(obj.data.current_condition[0].weatherDesc[0].value);
		var vrijemeDan1 = "<p>Vrijeme: " + obj.dan1.vrijeme + "</p>" + 
						  "<p>Temperatura: " + obj.dan1.temperatura + "&deg;C</p>";

		var vrijemeDan2 = "<p>Vrijeme: " + obj.dan2.vrijeme + "</p>" + 
						  "<p>Temperatura: " + obj.dan2.temperatura + "&deg;C</p>";

		var vrijemeDan3 = "<p>Vrijeme: " + obj.dan3.vrijeme + "</p>" + 
						  "<p>Temperatura: " + obj.dan3.temperatura + "&deg;C</p>";
			                     
		$("#izvjestajOVremenuDan1").html(vrijemeDan1);
		$("#izvjestajOVremenuDan2").html(vrijemeDan2);
		$("#izvjestajOVremenuDan3").html(vrijemeDan3);

		$("#weatherImageDan1").attr("src", postaviIkonu(obj.dan1.vrijeme));
		$("#weatherImageDan2").attr("src", postaviIkonu(obj.dan2.vrijeme));
		$("#weatherImageDan3").attr("src", postaviIkonu(obj.dan3.vrijeme));

		var grad = $("#navInputFieldNaredniDani").val();
		
		var latitude = 0;
		var longitude = 0;
		var zaGrad;

		$.ajax({url: "http://maps.google.com/maps/api/geocode/json?address=" + grad + "&sensor=false&region=ba", success: function(coordinates){
        	latitude = coordinates.results[0].geometry.location.lat;
            longitude = coordinates.results[0].geometry.location.lng;
            zaGrad = coordinates.results[0].address_components[0].long_name;
            $("#vrijemeZaGrad").html(zaGrad);
            azurirajMapu(latitude, longitude);
    	}});

		$("#mainInputFieldNaredniDani").val("");
		$("#navInputFieldNaredniDani").val("");
		

	}
	else{
		alert("nije uspjelo");
	}
}

function prikaziVrijemeNarednaTriDana(data, status, jqXHR){
	if(status == "success"){
		var x = JSON.stringify(data);
		var obj = jQuery.parseJSON(x);
		//alert(obj.data.current_condition[0].weatherDesc[0].value);
		var vrijemeDan1 = "<p>Vrijeme: " + obj.dan1.vrijeme + "</p>" + 
						  "<p>Temperatura: " + obj.dan1.temperatura + "&deg;C</p>";

		var vrijemeDan2 = "<p>Vrijeme: " + obj.dan2.vrijeme + "</p>" + 
						  "<p>Temperatura: " + obj.dan2.temperatura + "&deg;C</p>";

		var vrijemeDan3 = "<p>Vrijeme: " + obj.dan3.vrijeme + "</p>" + 
						  "<p>Temperatura: " + obj.dan3.temperatura + "&deg;C</p>";
			                     
		$("#izvjestajOVremenuDan1").html(vrijemeDan1);
		$("#izvjestajOVremenuDan2").html(vrijemeDan2);
		$("#izvjestajOVremenuDan3").html(vrijemeDan3);

		$("#weatherImageDan1").attr("src", postaviIkonu(obj.dan1.vrijeme));
		$("#weatherImageDan2").attr("src", postaviIkonu(obj.dan2.vrijeme));
		$("#weatherImageDan3").attr("src", postaviIkonu(obj.dan3.vrijeme));

		var grad = $("#mainInputFieldNaredniDani").val();
		
		var latitude = 0;
		var longitude = 0;
		var zaGrad;

		$.ajax({url: "http://maps.google.com/maps/api/geocode/json?address=" + grad + "&sensor=false&region=ba", success: function(coordinates){
        	latitude = coordinates.results[0].geometry.location.lat;
            longitude = coordinates.results[0].geometry.location.lng;
            zaGrad = coordinates.results[0].address_components[0].long_name;
            $("#vrijemeZaGrad").html(zaGrad);
            azurirajMapu(latitude, longitude);
    	}});

		$("#mainInputFieldNaredniDani").val("");
		$("#navInputFieldNaredniDani").val("");
		

	}
	else{
		alert("nije uspjelo");
	}
}

function prikaziVrijeme(data, status, jqXHR){
	if(status == "success"){
		var x = JSON.stringify(data);
		var obj = jQuery.parseJSON(x);
		//alert(obj.data.current_condition[0].weatherDesc[0].value);
		var vrijeme = vrijeme = "<p>Vrijeme: " + obj.data.current_condition[0].weatherDesc[0].value + "</p>" + 
								"<p>Temperatura: " + obj.data.current_condition[0].temp_C + "&deg;C</p>" + 
			                     "<p>Pritisak zraka: " + obj.data.current_condition[0].pressure + " hPa</p>" +
			                     "<p>Vlaznost zraka: " + obj.data.current_condition[0].humidity + " %</p>" +
			                     "<p>Brzina vjetra: " + obj.data.current_condition[0].windspeedKmph + " km/h</p>";
		$("#izvjestajOVremenu").html(vrijeme);

		$("#weatherImage").attr("src", postaviIkonu(obj.data.current_condition[0].weatherDesc[0].value));

		var latitude = obj.data.nearest_area[0].latitude;
		var longitude = obj.data.nearest_area[0].longitude;

		var zaGrad = obj.data.nearest_area[0].areaName[0].value;

		azurirajMapu(latitude, longitude);

		$("#mainInputField").val("");
		$("#navInputField").val("");
		$("#vrijemeZaGrad").html(zaGrad);

	}
	else{
		alert("nije uspjelo");
	}
}

function azurirajMapu(latitude, longitude){

	var lat = parseFloat(latitude)
	var lon = parseFloat(longitude)

	var lokacija = {lat: lat, lng: lon};
        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 10,
          center: lokacija
        });
        var marker = new google.maps.Marker({
          position: lokacija,
          map: map
        });
}

function postaviIkonu(vrijeme){

	vrijeme = vrijeme.toLowerCase();
	
	if(vrijeme.indexOf('sunny') !== -1){
        return "resources/images/sunny.jpg";
    }
    else if(vrijeme.indexOf('rain') !== -1){
        return "resources/images/rain.jpg";
    }
    else if(vrijeme.indexOf('snow') !== -1){
        return "resources/images/snow.jpg";
    }
    else if(vrijeme.indexOf('cloudy') !== -1 || vrijeme.indexOf('overcast') !== -1){
        return "resources/images/clouds.jpg";
    }
    else if(vrijeme.indexOf('light drizzle') !== -1){
        return "resources/images/rain.jpg";
    }
    else if(vrijeme.indexOf('fog') !== -1 || vrijeme.indexOf('mist') !== -1){
        return "resources/images/fog.jpg";
    }
    return "";
}

function initMap(data, status, jqXHR){
	var x = JSON.stringify(data);
	var obj = jQuery.parseJSON(x);
	
	var latitude = obj.lat;
	var longitude = obj.lon;

	var lokacija = {lat: latitude, lng: longitude};
        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 10,
          center: lokacija
        });
        var marker = new google.maps.Marker({
          position: lokacija,
          map: map
        });
}


