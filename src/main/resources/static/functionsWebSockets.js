
var webSocketTimer = setInterval(doSend, 100);

var path = "/ProjektSkutekKrystian/webSocketEndpointJSON";

function initWebSocket(path) {
    websocket = new WebSocket(getRootUri() + path);

    websocket.onopen = function (evt) {
        onOpen(evt);
    };

    websocket.onmessage = function (evt) {
        onMessage(evt);
    };

    websocket.onerror = function (evt) {
        onError(evt);
    };
}

function onOpen(evt) {
    writeToScreen("CONNECTED to websocketJSON");
    console.log("CONNECTED to websocketJSON");
}

function onMessage(evt) {
    writeToScreen("RECEIVED from websocketJSON: " + evt.data);

    var data = JSON.parse(evt.data);

    for (i = 0; i < data.length; i++) {
        dataPointsParam[i] = {label: i, y: data[i]};
    }

    drawChart(dataPointsParam);
}

function onError(evt) {
    writeToScreen('ERROR: JSON websocket nie działa' + evt.data);
    console.log(evt.data);
}

function doSend(message) {
    writeToScreen("SENT: " + message);

    if (message) {
        // Wysyłanie danych przez odpowiedni Websocket
        if (websocket.readyState === WebSocket.OPEN) {
            websocket.send(message);
        }
    } else {
        websocket.send("x");
    }
}

function getRootUri() {
    var wsUriHostPort = location.protocol === "http:" ? "ws://" : "wss://";
    return (
            wsUriHostPort +
            (document.location.hostname === "localhost"
                    ? "localhost"
                    : document.location.hostname) +
            ":" +
            (document.location.port === "" ? "8080" : document.location.port)
            );
}

function writeToScreen(message) {
    var messageField = document.getElementById("messageStatusID");

    messageField.value = message;
}


// OK
function drawChart(dataPointsParam) {
    var chart = new CanvasJS.Chart("chartContainer", {
        title: {
            text: "Wykres CanvasJS",
        },
        axisY: {
            title: "Opis osi Y",
            maximum: 100,
        },
        data: [
            {
                type: "column",
                dataPoints: dataPointsParam,
            },
        ],
    });
    chart.render();
}

window.addEventListener("load", init, false);

var dataPointsParam = [];

for (var i = 0; i < 50; i++) {
    dataPointsParam[i] = {label: i, y: 0};
}

function init() {
    drawChart(dataPointsParam);
    initWebSocket(path);
}