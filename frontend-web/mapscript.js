/*var coords = '[{latitude": 49.407787,"longitude": 8.693913},{"latitude": -25.274398,"longitude": 133.775136}]';

var myData = JSON.parse(coords); */
/*
$(document).ready(function() {
    $.each(myData, function() {
        $('<li>' + this.latitude + ' ' + this.longitude + '</li>').appendTo("#groups");
    });
});
*/



function initMap() {
    var uluru = {lat: -25.363, lng: 131.044};
    var map = new google.maps.Map(document.getElementById('map'), {
      zoom: 1,
      center: uluru
    });
    /*var marker = new google.maps.Marker({
      position: uluru,
      map: map
    });*/
    var articlejson;
	$(document).ready(function(){
        $.getJSON("http://10.52.1.114:8080/api/article/full_list", function(json){
            let articlejson = json
            console.log(articlejson)
            setMarkers(map, articlejson)
        });
});
    

}


function setMarkers(map, articlejson) {
  // Adds markers to the map.

  // Marker sizes are expressed as a Size of X,Y where the origin of the image
  // (0,0) is located in the top left of the image.

  // Origins, anchor positions and coordinates of the marker increase in the X
  // direction to the right and in the Y direction down.
  var image = {
    url: 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png',
    // This marker is 20 pixels wide by 32 pixels high.
    size: new google.maps.Size(20, 32),
    // The origin for this image is (0, 0).
    origin: new google.maps.Point(0, 0),
    // The anchor for this image is the base of the flagpole at (0, 32).
    anchor: new google.maps.Point(0, 32)
  };
  // Shapes define the clickable region of the icon. The type defines an HTML
  // <area> element 'poly' which traces out a polygon as a series of X,Y points.
  // The final coordinate closes the poly by connecting to the first coordinate.
  var shape = {
    coords: [1, 1, 1, 20, 18, 20, 18, 1],
    type: 'poly'
  };
  for (var i = 0; i < articlejson.length; i++) {
    let beach = articlejson[i];
    let marker = new google.maps.Marker({
      position: {lat: beach.lat, lng: beach.lng},
      map: map,
      icon: image,
      shape: shape,
      url: beach.id
    });
    google.maps.event.addListener(marker, 'click', function() {
    	$("#startmap").css("display","none");
    	addcontent(marker.url, articlejson)
    	$("#story").css("display","block");
    
	});
  }
}


// STORY
function addcontent(storyid,jsonarticle){
  document.getElementById("headline").innerHTML = jsonarticle[storyid-1].headline
  document.getElementById("abstract").innerHTML = jsonarticle[storyid-1].abstract
  for (i=0;i<jsonarticle[storyid-1].content.length;i++){
    if(jsonarticle[storyid-1].content[i][0] = "img"){
      document.getElementById("content").innerHTML += '<img class="cont" src='+jsonarticle[storyid-1].content[i][1].toString()+'>'
    }
    else if(jsonarticle[storyid-1].content[i][0] = "video"){
      document.getElementById("content").innerHTML += '<embed class="cont" src='+jsonarticle[storyid-1].content[i][1].toString()+'>'
    }
    else{
    document.getElementById("content").innerHTML += '<div class="cont">'+jsonarticle[storyid-1].content[i][1].toString()+'</div>'
    }
  }
}

function returntomap(){
	$("#startmap").css("display","block");
    document.getElementById("content").innerHTML = ""
    $("#story").css("display","none");
}





//<img id="wmap" src="globe-32299.svg">