
//UTILITY
function getElementByXpath(path) {
    return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
}
Date.prototype.toShortFormat = function () {

    var month_names = ["Jan", "Feb", "Mar",
        "Apr", "May", "Jun",
        "Jul", "Aug", "Sep",
        "Oct", "Nov", "Dec"];

    var day = this.getDate();
    var month_index = this.getMonth();
    var year = this.getFullYear();

    return "" + day + " " + month_names[month_index] + " " + year;
}

Date.prototype.addHours = function(h) {
    this.setTime(this.getTime() + (h*60*60*1000));
    return this;
}

function changeSpringDateToJSDate(springDateString) {
    var dateStr = JSON.stringify(springDateString).substr(1, 10);
    var date = new Date(dateStr.replace(/-/g, '\/'));
    date.addHours(8);
    return date;
}

function calculateAge(springDateString) {
    return new Date().getFullYear() - changeSpringDateToJSDate(springDateString).getFullYear();
}



//LIBRARY
function callCKEditor(){
    if($("#" + "editor1").length != 0  &&
        $("#" + "editor2").length != 0 ) {
        CKEDITOR.replace('editor1', {
            height: 260,
            width: 700,
        });
        CKEDITOR.replace('editor2', {
            height: 260,
            width: 700,
        });
    }
}

$('#carouselExampleIndicators').carousel({
    interval: false
});






// PUSH NOTIFICATION
var header = $('#header');
var content = $('#content');
var input = $('#input');
var status = $('#status');
var myName = false;
var author = null;
var logged = false;
var socket = atmosphere;
var subSocket;
var transport = 'websocket';

// We are now ready to cut the request
var request = {
    url : '/chat',
    contentType : "application/json",
    logLevel : 'debug',
    transport : transport,
    trackMessageLength : true,
    reconnectInterval : 5000
};

request.onOpen = function(response) {
    console.log('Atmosphere connected using ' + response.transport);
    transport = response.transport;

    // Carry the UUID. This is required if you want to call
    // subscribe(request) again.
    request.uuid = response.request.uuid;
};

request.onClientTimeout = function(r) {
    console.log('Client closed the connection after a timeout. Reconnecting in '
        + request.reconnectInterval);
    subSocket
        .push(atmosphere.util
            .stringifyJSON({
                author : author,
                message : 'is inactive and closed the connection. Will reconnect in '
                    + request.reconnectInterval
            }));
    input.attr('disabled', 'disabled');
    setTimeout(function() {
        subSocket = socket.subscribe(request);
    }, request.reconnectInterval);
};

request.onReopen = function(response) {
    console.log('Atmosphere re-connected using ' + response.transport);
};

// For demonstration of how you can customize the fallbackTransport using
// the onTransportFailure function
request.onTransportFailure = function(errorMsg, request) {
    atmosphere.util.info(errorMsg);
    console.log('Atmosphere Chat. Default transport is WebSocket, fallback is '
        + request.fallbackTransport);

    request.fallbackTransport = "long-polling";
};

request.onClose = function(response) {
    console.log('Server closed the connection after a timeout');
    if (subSocket) {
        subSocket.push(JSON.stringify({
            author : author,
            message : 'disconnecting'
        }));
    }
    input.attr('disabled', 'disabled');
};

request.onError = function(response) {
    console.log('Sorry, but there\'s some problem with your '
        + 'socket or the server is down');
    logged = false;
};

request.onReconnect = function(request, response) {
    console.log('Connection lost, trying to reconnect. Trying to reconnect '
        + request.reconnectInterval);
};

//react dựa trên nội dung message
request.onMessage = function(response) {

    var message = response.responseBody;
    try {
        var json = JSON.parse(message);
    } catch (e) {
        console.log('This doesn\'t look like a valid JSON: ', message);
        return;
    }

    if (json.receiver == currentExaminator && json.message == "LOADEXAMINATIONS"){
            loadExaminations(currentExaminator);
    } else if (json.receiver == currentDoctor  && json.message == "LOADEXAMINATIONTOAPPOINTMENT") {
            loadExaminationToAppointment(currentAppointment);
            loadExaminationsToModal();
    }
};

subSocket = socket.subscribe(request);

//gửi message đến tất cả các client
function sendBroadcast(sender, message, receiver){
    subSocket.push(JSON.stringify({
        sender : sender,
        receiver: receiver,
        message : message
     }));
}