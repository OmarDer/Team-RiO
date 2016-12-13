$( document ).ready(function main(){
	initMap();
	initializeEvents();
});

var map;

function getWeatherForCity(grad){

	var weatherURL = "http://api.worldweatheronline.com/premium/v1/weather.ashx?q=" + grad + ",ba&key=67f8e93988414397bf2234724161212&fx=no&includelocation=yes&mca=no&format=json";

	$.ajax({
	  url: weatherURL,
	  success: postaviMarkerNaMapu,
	  dataType:'json',
      type: 'get',
	});

}

function postaviMarkerNaMapu(data, status, jqXHR){

	if(status == "success"){
		var x = JSON.stringify(data);
		var obj = jQuery.parseJSON(x);
		
		var vrijeme = obj.data.current_condition[0].weatherDesc[0].value; 
		var temp = obj.data.current_condition[0].temp_C; 
		var pritisak = obj.data.current_condition[0].pressure;
		var vlaznost = obj.data.current_condition[0].humidity;
		var brzinaVjetra = obj.data.current_condition[0].windspeedKmph;
		var latitude = obj.data.nearest_area[0].latitude;
		var longitude = obj.data.nearest_area[0].longitude;
		var zaGrad = obj.data.nearest_area[0].areaName[0].value;

		//alert("temp u Sarajevu: " + temp);

		var lat = parseFloat(latitude)
		var lon = parseFloat(longitude)

		var lokacija = {lat: lat, lng: lon};

        var contentString = "<p><strong>Vrijeme: </strong>" + vrijeme + "</p>" + 
							"<p><strong>Temperatura: </strong>" + temp + "&deg;C</p>" + 
			                "<p><strong>Pritisak zraka: </strong>" + pritisak + " hPa</p>" +
			                "<p><strong>Vlaznost zraka: </strong>" + vlaznost + " %</p>" +
			                "<p><strong>Brzina vjetra: </strong>" + brzinaVjetra + " km/h</p>";

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

function initializeEvents(){

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





