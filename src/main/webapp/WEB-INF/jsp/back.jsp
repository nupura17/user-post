



<!DOCTYPE html>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
<html>
<body>

<p>Click the button to get your coordinates.</p>

<button onclick="getLocation('Mumbai')">Try It</button>

<p id="demo"></p>

<script>
    var x = document.getElementById("demo");

    function getLocation(address) {
        var geocoder = new google.maps.Geocoder();
        geocoder.geocode( { 'address': address}, function(results, status) {

            if (status == google.maps.GeocoderStatus.OK) {
                var latitude = results[0].geometry.location.lat();
                var longitude = results[0].geometry.location.lng();
                console.log(latitude, longitude);
            }
        });
    }

    function showPosition(position) {
        x.innerHTML = "Latitude: " + position.coords.latitude +
            "<br>Longitude: " + position.coords.longitude;
    }
</script>

</body>
</html>
