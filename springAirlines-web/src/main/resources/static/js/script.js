function connect() {
	$('#connect_btn').attr("disabled", true)
	$('#send_btn').attr("disabled", false)
	$('#disconnect_btn').attr("disabled", false)
	$('#message_input_value').attr("disabled", false)

	var socket = new SockJS('/chat-messaging');
	stompClient = Stomp.over(socket);

	stompClient.connect({}, function(frame) {
		console.log("connected: " + frame);
		stompClient.subscribe('/chat/messages', function(response) {
			var data = JSON.parse(response.body);
			var date = new Date(data.createdDate);
			draw(data.username, format(date), data.text);
		});
	});
}

function format(date) {
	var d = new Date(date),
		month = '' + (d.getMonth() + 1),
		day = '' + d.getDate(),
		year = d.getFullYear();

	if (month.length < 2) month = '0' + month;
	if (day.length < 2) day = '0' + day;

	return [day, month, year].join('.');
}

function draw(from, time, text) {

	console.log("drawing...");
    var $message;
    $message = $($('.message_template').clone().html());
	$message.addClass("left").find('.from').html(from + " " + time);
    $message.addClass("left").find('.text').html(text);
    $('.messages').append($message);
    return setTimeout(function () {
        return $message.addClass('appeared');
    }, 0);

}
function disconnect(){
	$('#connect_btn').attr("disabled", false)
	$('#send_btn').attr("disabled", true)
	$('#disconnect_btn').attr("disabled", true)
	$('#message_input_value').attr("disabled", true)

	stompClient.disconnect();
}

function sendMessage(){
	stompClient.send("/app/message", {}, JSON.stringify({
		'from': $("#from_input_value").val(),
		'text': $("#message_input_value").val(),
	}));

	$('#message_input_value').val('')
}
