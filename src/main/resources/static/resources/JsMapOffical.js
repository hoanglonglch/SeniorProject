var markers = [];
var url = window.location.href;
var trend;
var currentTrend;
var renderList=[];


function openNav() {
    document.getElementById("mySidenav").style.width = "30%";
}

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
}

function initMap() {
    let directionsService = new google.maps.DirectionsService;
    var directionsDisplay=new google.maps.DirectionsRenderer;
    let geocoder = new google.maps.Geocoder;
    let geocoder2=new google.maps.Geocoder;
    let infowindow = new google.maps.InfoWindow;
    let currentRoute;
    var styledMapType = customizeMap();
    
    let directionTab=$("#directions-tab").html();
    let routesTab=$("#routes-tab").html();
    
    
    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 12
        , center: {
            lat: 16.054856
            , lng: 108.200403
        }
        , mapTypeControlOptions: {
            mapTypeIds: ['roadmap', 'terrain'
                    , 'styled_map']
        }
    });
    map.mapTypes.set('styled_map', styledMapType);
    map.setMapTypeId('styled_map');
    
    directionsDisplay.setMap(map);
    directionsDisplay.setPanel(document.getElementById('direction-content'));
    
    $("body").on("click", ".btn-back", function (event) {
        clearMarkers();
        $("#routes-tab").html(routesTab);
        markers = [];
        clearPolyline(renderList);
        renderList=[];
    });
    
    $("body").on("click", ".rowClear", function (event) {
        clearMarkers();
        markers = [];
        clearPolyline(renderList);
        renderList=[];
        let spotId = $(this).find(".spotId").text();
        sideBarSpotGetContent(url, spotId);
        mapDrawMarker(url, spotId,map,infowindow,geocoder);
    });
    
    $("body").on("click",".button",function(event){
    	let spotId1= $(this).attr('id');
        clearMarkers();
        markers = [];
    	sendAddress(spotId1,map,directionsService,geocoder,infowindow,directionsDisplay,geocoder);
    });
    
}

function sendAddress(spotId1,map,directionsService,geocoder,infowindow,directionsDisplay,geocoder2){
    let startPoint=$("#startPoint").val();
    alert(startPoint+" "+spotId1);
    ajaxDirection(spotId1,startPoint,map,directionsService,geocoder,infowindow,directionsDisplay);
//    mapDrawMarker(url, spotId1,map,infowindow,geocoder);
    
}
function ajaxDirection(spotId1,startPoint,map,directionsService,geocoder,infowindow,directionsDisplay) {
   var getUrl = url + "/direction" ;
    $.ajax({
        type: "GET"
        , url: getUrl
        , data: {
            startPoint:startPoint,
            spotId1:spotId1
        }
        , success: function (data) {
        	 directionsDisplay.setMap(map);
        	    directionsDisplay.setPanel(document.getElementById('direction-content'));
        	drawDirection(data,map,directionsService,directionsDisplay);
        	 directionsDisplay.setMap(map);
        	    directionsDisplay.setPanel(document.getElementById('direction-content'));
        	for(let i=0;i<data.length;i++){
       		 markers.push(createMarker(data[i].lat,data[i].lng, map));
       		 markers[i].addListener('click', function () {
       	        	map.setCenter(markers[i].getPosition());
       	           geocodeLatLng(geocoder, map, infowindow,data[i].lat,data[i].lng, data[i].name);
       	            infowindow.open(map, markers[i]);
       	  });
       	}
        	
        }
    });
}


function sideBarSpotGetContent(url, spotId) {
    var getUrl = url + "/spot/" + spotId;
    $.ajax({
        type: "GET"
        , url: getUrl
        , success: function (data) {
            var html = jQuery('<body>').html(data);
            var content = html.find("#station-detail").html();
            $("#routes-tab").html(content);
             showMarkerDetail(markers);
        }
    });
}
function mapDrawMarker(url, spotId,map,infowindow,geocoder){
	 var getUrl = url + "/spots" ;
	    $.ajax({
	        type: "GET"
	        , url: getUrl
	        , data: {
	        	spotId:spotId
	        }
	        , success: function (data) {
	        	// create marker here
	        	let markerT;
	        	for(let i=0;i<data.length;i++){
	        		 markers.push(createMarker(data[i].lat,data[i].lng, map));
	        		 markers[i].addListener('click', function () {
	        	        	map.setCenter(markers[i].getPosition());
	        	           geocodeLatLng(geocoder, map, infowindow,data[i].lat,data[i].lng, data[i].name);
	        	            infowindow.open(map, markers[i]);
	        	  });
	        	}
	        }
	    });
}

function sideBarDirection(url,startPoint,endPoint) {
	var getUrl = url + "/direction" ;
	$.ajax({
		type: "GET"
			, url: getUrl
			, data: {
				startPoint:startPoint,
				endPoint:endPoint
			}
	, success: function (data) {
		let html = jQuery('<body>').html(data);
        let content = html.find("#contentDirection").html();
        $("#direction-content").html(content);
         showMarkerDetail(markers);
	}
	});
}

function getBack(url) {
    $.ajax({
        type: "GET"
        , url: url
        , success: function (data) {
            var html = jQuery('<body>').html(data);
            var content = html.find("#content").html();
            $("#content").html(content);
        }
    });
}

function setMapOnAll(map) {
    for (let i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
    }
}

function clearMarkers() {
    setMapOnAll(null);
}

function parseLat(str) {
    let temp = [];
    temp = str.split(',');
    let number = parseFloat(temp[0]);
    return number;
}

function parseLng(str) {
    let temp = [];
    temp = str.split(',');
    let number = parseFloat(temp[1]);
    return number;
}

function callAjax(url, routeId, trend, directionsService, directionsDisplay, map, geocoder, infowindow) {
// let busRoute = "route" + routeId;
    $.ajax({
        type: "GET"
        , contentType: "application/json"
        , url: url + "/ajax"
        , data: {
            busRoute: routeId
            , trend: trend
        }
        , dataType: 'json'
        , timeout: 100000
        , success: function (jsonResponse) {
            calculateAndDisplayRoute1(directionsService, directionsDisplay, jsonResponse, map, geocoder, infowindow);
        }
    });
}

function calculateAndDisplayRoute1(directionsService, directionsDisplay, jsonResponse, map, geocoder, infowindow) {
    // load waypoint from server
    let totalDataLength = jsonResponse.length;
    let waypts = [];
    let marker;
    let startIcon = "https://chart.googleapis.com/chart?chst=d_map_xpin_icon&chld=pin_star|car-dealer|ADDE63|FF0000";
    let endIcon = "https://chart.googleapis.com/chart?chst=d_map_pin_icon&chld=flag|ADDE63";
    let wayPointsIcon;
    let lat;
    let lng;
    for (let i = 0; i < jsonResponse.length; i++) {
        lat =jsonResponse[i].lat;
        lng = jsonResponse[i].lng;
        wayPointsIcon = "https://chart.googleapis.com/chart?chst=d_map_pin_letter&chld=" + (i + 1) + "|FF0000|000000";
        if (i == 0) {
        	
            marker = createMarker(lat, lng, startIcon, map);
            markers.push(marker);
        }
        else if (i > 0 && i < jsonResponse.length - 1) {
            marker = createMarker(lat, lng, wayPointsIcon, map);
            markers.push(marker);
            waypts.push({
                location: new google.maps.LatLng(lat, lng)
                , stopover: true
            });
        }
        else {
            marker = createMarker(lat, lng, endIcon, map);
            markers.push(marker);
        }
        markers[i].addListener('click', function () {
        	map.setCenter(markers[i].getPosition());
           geocodeLatLng(geocoder, map, infowindow,	 lat,lng, jsonResponse[i].name);
            infowindow.open(map, markers[i]);
        });
    }
    drawDirection(jsonResponse,map,directionsService,directionsDisplay);
}

function createMarker(lat, lng, map) {
    let marker = new google.maps.Marker({
        position: new google.maps.LatLng(lat, lng),icon:{
            url:    'http://maps.google.com/mapfiles/kml/paddle/wht-stars.png',scaledSize: new google.maps.Size(40, 40)

          } 
        , map: map
    });	
    return marker;
}
function showMarkerDetail(markers){
	let stationsName = $(".stationsName");
	for(let j=0;j<stationsName.length;j++){
		google.maps.event.addDomListener(stationsName[j],"click",function(){
			google.maps.event.trigger(markers[j],"click");
		});
	}
}

/* backup if markers = 0 */
function showMarkerDetail1(markers){
    let markersLength=markers.length;
    let stationsName=$(".stationsName");
    if(markersLength>0 && ( typeof(stationsName) != "underfined") ){
        for(let j=0;j<stationsName.length;j++){
        console.log("stations name object "+stationsName[j]);
            
        google.maps.event.addDomListener(stationsName[j], "click", function () {
                google.maps.event.trigger(markers[j], "click");
        });
    }
    }else{
        alert("0");
    }
}
function geocodeLatLng(geocoder, map, infowindow, lat1,lng1, nameStation) {
    let latlng = {
        lat: lat1
        , lng:lng1
    };
    geocoder.geocode({
        'location': latlng
    }, function (results, status) {
        if (status === 'OK') {
            if (results[0]) {
                let contentString = '<div id="infoContent" style="height:85px;width:355px">' + '<div id="siteNotice">' + '</div>' + '<div id="bodyContent" >' + '<p><b>Tên địa điểm:    </b>' + nameStation + '</p>' + '<p><b>Địa chỉ:    </b>' + results[0].formatted_address + '</p>' + '</div>' + '</div>';
                infowindow.setContent(contentString);
            }
            else {
                window.alert('No results found');
            }
        }
        else {
            window.alert('Geocoder failed due to: ' + status);
        }
    });
}
function clearPolyline(renderList){
    for(let i=0;i<renderList.length;i++){
        renderList[i].setMap(null);
    }
}
function drawDirection(stations,map,service){

    var lngs = stations.map(function (station) {
        return station.lng;
    });
    var lats = stations.map(function (station) {
        return station.lat;
    });
    map.fitBounds({
        west: Math.min.apply(null, lngs)
        , east: Math.max.apply(null, lngs)
        , north: Math.min.apply(null, lats)
        , south: Math.max.apply(null, lats)
    , });
    // Divide route to several parts because max stations limit is 25 (23
	// waypoints + 1 origin + 1 destination)
    for (var i = 0, parts = [], max = 25-1; i < stations.length; i = i + max) parts.push(stations.slice(i, i + max + 1));
    // Callback function to process service results
    var service_callback = function (response, status) {
                if (status != 'OK') {
                    console.log('Directions request failed due to ' + status);
                    return;
                }
                var renderer = new google.maps.DirectionsRenderer;
                renderer.setMap(map);
                renderer.setOptions({
                    suppressMarkers: true
                    , preserveViewport: true
                });
                renderList.push(renderer);
                
                var steps;
                try {
                	steps = response['routes'][0]['legs'][0]['steps'];
				} catch (e) {
					steps = [];
				}
				var i = 0;
				var html = '';
				for(i; i< steps.length; i++) {
					html += `<div class="row row-eq-height rowClear row-bordered">
							<div class="col-md-1">
								<p class="spotId">`+(i+1)+`</p>
							</div>
							<div class="col-md-11" >
								`+steps[i]['instructions']+`
							</div>
						</div>`; 
				}
				console.log('dm', response, steps)
                $('#direction-content').html(html);
                
                renderer.setDirections(response);
    };
    // Send requests to service to get route (for stations count <= 25 only one
	// request will be sent)
    for (var i = 0; i < parts.length; i++) {
        // Waypoints does not include first station (origin) and last station
		// (destination)
        var waypoints = [];
        for (var j = 1; j < parts[i].length - 1; j++) waypoints.push({
            location: parts[i][j]
            , stopover: false
        });
        // Service options
        var service_options = {
            origin: parts[i][0]
            , destination: parts[i][parts[i].length - 1]
            , waypoints: waypoints
            , travelMode: 'DRIVING'
        };
        // Send request
        service.route(service_options, service_callback);
    }
}
function customizeMap() {
    let styledMapType = new google.maps.StyledMapType(
			    [
            {
                "featureType": "poi"
                , "stylers": [
                    {
                        "color": "#ff3b88"
		      }
		                      , {
                        "visibility": "off"
		      }
		    ]
		  }
		              , {
                "featureType": "poi.attraction"
                , "stylers": [
                    {
                        "visibility": "off"
		      }
		    ]
		  }
		              , {
                "featureType": "poi.government"
                , "stylers": [
                    {
                        "visibility": "off"
		      }
		    ]
		  }
		              , {
                "featureType": "poi.medical"
                , "stylers": [
                    {
                        "visibility": "off"
		      }
		    ]
		  }
		              , {
                "featureType": "poi.school"
                , "stylers": [
                    {
                        "visibility": "off"
		      }
		    ]
		  }
		              , {
                "featureType": "poi.sports_complex"
                , "stylers": [
                    {
                        "visibility": "off"
		      }
		    ]
		  }
		], {
            name: 'Styled Map'
        });
    return styledMapType;
}