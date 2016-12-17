$( document ).ready(function main(){
	initMap();
	initializeEvents();
});

var map;

function getWeatherForCity(grad){

	var weatherURL = "http://localhost:8084/spvp/prognozatridana/" + grad;

	$.ajax({
	  url: weatherURL,
	  success: function(data, success, jqXHR){ postaviMarkerNaMapu(grad, data, success, jqXHR); },
	  dataType:'json',
      type: 'get',
	});

}

function postaviMarkerNaMapu(grad, data, status, jqXHR){

	if(status == "success"){
		var x = JSON.stringify(data);
		var obj = jQuery.parseJSON(x);
		
		var vrijeme = obj.dan1.vrijeme;
		var dani = dajDanePrekosutra();

		var vrijemeDan1 = 	"<p><strong>Sutra</strong></p>" + 
							"<img src='" + postaviIkonu(obj.dan1.vrijeme) + "' />" +
						  	"<p><strong>Vrijeme: </strong>" + obj.dan1.vrijeme + "</p>" + 
						  	"<p><strong>Temperatura: </strong>" + obj.dan1.temperatura + "&deg;C</p>";

		var vrijemeDan2 = 	"<p><strong>"+dani[0]+"</strong></p>" +
							"<img src='" + postaviIkonu(obj.dan2.vrijeme) + "' />" +
							"<p><strong>Vrijeme: </strong>" + obj.dan2.vrijeme + "</p>" + 
						  	"<p><strong>Temperatura: </strong>" + obj.dan2.temperatura + "&deg;C</p>";

		var vrijemeDan3 = 	"<p><strong>"+dani[1]+"</strong></p>" +
							"<img src='" + postaviIkonu(obj.dan3.vrijeme) + "' />" +
							"<p><strong>Vrijeme: </strong>" + obj.dan3.vrijeme + "</p>" + 
						  	"<p><strong>Temperatura: </strong>" + obj.dan3.temperatura + "&deg;C</p>";

		var latitude = 0;
		var longitude = 0;
		var zaGrad;

		$.ajax({url: "http://maps.google.com/maps/api/geocode/json?address=" + grad + "&sensor=false&region=ba", success: function(coordinates){
        	
        	latitude = coordinates.results[0].geometry.location.lat;
            longitude = coordinates.results[0].geometry.location.lng;
            zaGrad = coordinates.results[0].address_components[0].long_name;

            var lat = parseFloat(latitude)
			var lon = parseFloat(longitude)

			var lokacija = {lat: lat, lng: lon};

	        var contentString = "<div class='row'>" +
	        					"<div class='col-lg-4'>" + vrijemeDan1 + "</div>" +
	        					"<div class='col-lg-4'>" + vrijemeDan2 + "</div>" +
	        					"<div class='col-lg-4'>" + vrijemeDan3 + "</div>" +
	        					"</div>";		

	        var infowindow = new google.maps.InfoWindow({
	          content: contentString
	        });

	        var marker = new google.maps.Marker({
	          position: lokacija,
	          map: map,
	          icon: postaviIkonu(vrijeme),
	          title: 'Vrijeme za ' + zaGrad + ', Bosna i Hercegovina'
	        });

	        marker.addListener('mouseover', function() {
	          infowindow.open(map, marker);
	        });

	        marker.addListener('mouseout', function() {
	          infowindow.close();
	        });

    	}});

	}
	else{
		alert("nije uspjelo");
	}
}

function initializeMapEvents(){

	google.maps.event.addListener(map, 'zoom_changed', function() {
    zoomChangeBoundsListener = 
        google.maps.event.addListener(map, 'bounds_changed', function(event) {
            if (this.getZoom() == 8) {
                
                iscrtajMapuSamoVeciCentri();
                
            }
            else if(this.getZoom() == 9){
            	iscrtajSve();
            }

        google.maps.event.removeListener(zoomChangeBoundsListener);
    });
	});

}

function dajDanePrekosutra(){
	
	var d = new Date();
	var weekday = new Array(7);
	weekday[0] = "Nedjelja";
	weekday[1] = "Ponedjeljak";
	weekday[2] = "Utorak";
	weekday[3] = "Srijeda";
	weekday[4] = "Cetvrtak";
	weekday[5] = "Petak";
	weekday[6] = "Subota";

	var dani = new Array(2);
	dani[0] = weekday[(d.getDay() + 2) % 7];
	dani[1] = weekday[(d.getDay() + 3) % 7];

	return dani;
}

function initializeEvents(){

	$('#navForm').submit(function(e) {
    	e.preventDefault();


    	var grad = $("#navInputField").val();
    	$("#navInputField").val("");

    	getWeatherForCity(grad);

        $.ajax({url: "http://maps.google.com/maps/api/geocode/json?address=" + grad + "&sensor=false&region=ba", success: function(coordinates){
            
            latitude = coordinates.results[0].geometry.location.lat;
            longitude = coordinates.results[0].geometry.location.lng;
            zaGrad = coordinates.results[0].address_components[0].long_name;

            var lat = parseFloat(latitude)
            var lon = parseFloat(longitude)

            var lokacija = {lat: lat, lng: lon};

            map.setCenter(lokacija);

            map.setZoom(10);

        }});

    
	});

}

function postaviIkonu(vrijeme){

	vrijeme = vrijeme.toLowerCase();
	
	if(vrijeme.indexOf('sunny') !== -1){
        return "resources/images/sun_icon.jpg";
    }
    else if(vrijeme.indexOf('rain') !== -1){
        return "resources/images/rain_icon.jpg";
    }
    else if(vrijeme.indexOf('snow') !== -1){
        return "resources/images/snow_icon.jpg";
    }
    else if(vrijeme.indexOf('cloudy') !== -1 || vrijeme.indexOf('overcast') !== -1){
        return "resources/images/clouds_icon.jpg";
    }
    else if(vrijeme.indexOf('light drizzle') !== -1){
        return "resources/images/rain_icon.jpg";
    }
    else if(vrijeme.indexOf('fog') !== -1 || vrijeme.indexOf('mist') !== -1){
        return "resources/images/fog_icon.jpg";
    }
    else if(vrijeme.indexOf('clear') !== -1){
        return "resources/images/clear_icon.jpg";
    }
    return "";
}

function iscrtajSve(){
	var lokacija = {lat: 43.915886, lng: 17.67907600000001};

	var stylesArray = [
	{
	    featureType: 'road',
        stylers: [{visibility: 'off'}]
	},
	{
	    featureType: 'transit',
        stylers: [{visibility: 'off'}]
	},
	{
	    featureType: 'water',
        stylers: [{visibility: 'off'}]
	},
	{
	    featureType: 'landscape.man_made',
        stylers: [{visibility: 'off'}]
	},
	{
	    featureType: 'administrative.province',
        stylers: [{visibility: 'off'}]
	}
	];

	

 	map = new google.maps.Map(document.getElementById('mapabih'), {
      zoom: 9,
      center: lokacija,
      styles: stylesArray
    });

    initializeMapEvents();

    getWeatherForCity("Sarajevo");
    getWeatherForCity("Zenica");
    getWeatherForCity("Tuzla");
    getWeatherForCity("Banja Luka");
    getWeatherForCity("Mostar");
    getWeatherForCity("Doboj");
    getWeatherForCity("Sanski Most");
    getWeatherForCity("Travnik");
    getWeatherForCity("Bugojno");
    getWeatherForCity("Konjic");
    getWeatherForCity("Neum");
    getWeatherForCity("Jablanica");
    getWeatherForCity("Bihac");
    getWeatherForCity("Trebinje");
    getWeatherForCity("Visegrad");
    getWeatherForCity("Livno");
    getWeatherForCity("Mrkonjic Grad");
    getWeatherForCity("Drvar");
    getWeatherForCity("Gradacac");
    getWeatherForCity("Bijeljina");
    getWeatherForCity("Gorazde");
    getWeatherForCity("Foca");
    getWeatherForCity("Stolac");
    getWeatherForCity("Ljubinje");
    getWeatherForCity("Derventa");
    getWeatherForCity("Gradiška");
    getWeatherForCity("Kakanj");
    getWeatherForCity("Vlasenica");
    getWeatherForCity("Visoko");
    getWeatherForCity("Fojnica");
    getWeatherForCity("Srebrenica");
    getWeatherForCity("Velika Kladusa");


}

function iscrtajMapuSamoVeciCentri(){

	var lokacija = {lat: 43.915886, lng: 17.67907600000001};

	var stylesArray = [
	{
	    featureType: 'road',
        stylers: [{visibility: 'off'}]
	},
	{
	    featureType: 'transit',
        stylers: [{visibility: 'off'}]
	},
	{
	    featureType: 'water',
        stylers: [{visibility: 'off'}]
	},
	{
	    featureType: 'landscape.man_made',
        stylers: [{visibility: 'off'}]
	},
	{
	    featureType: 'administrative.province',
        stylers: [{visibility: 'off'}]
	}
	];

	

 	map = new google.maps.Map(document.getElementById('mapabih'), {
      zoom: 8,
      center: lokacija,
      styles: stylesArray
    });

    getWeatherForCity("Sarajevo");
    getWeatherForCity("Zenica");
    getWeatherForCity("Tuzla");
    getWeatherForCity("Banja Luka");
    getWeatherForCity("Mostar");
    getWeatherForCity("Bihac");
    getWeatherForCity("Trebinje");
    getWeatherForCity("Visegrad");
    getWeatherForCity("Doboj");
    getWeatherForCity("Bugojno");
    getWeatherForCity("Livno");

    initializeMapEvents();

}

function initMap(){

	iscrtajMapuSamoVeciCentri();

}








