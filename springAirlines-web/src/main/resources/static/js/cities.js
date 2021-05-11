$.ajax({
    url: "/api/cities",
    type: "GET",
    dataType: "json",
    success: function (data) {
        let str = ""
        data.forEach(function (city) {
            str += city['name'] + " "
        })
        console.log(str)
        $('#tripsBlock').html("Trips to " + str)
    }
});