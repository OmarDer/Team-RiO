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


