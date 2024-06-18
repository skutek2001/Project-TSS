package com.tss.websockets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketEndpointJSON extends AbstractWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(WebSocketEndpointJSON.class);

    static java.util.Random randomizer = new java.util.Random();
    static int dataSize = 50;
    static int[] dataPointsParam = new int[dataSize];

    public WebSocketEndpointJSON() {
        for (int i = 0; i < dataSize; i++) {
            dataPointsParam[i] = randomizer.nextInt(100);

            if (dataPointsParam[i] > 100) {
                dataPointsParam[i] = 100;
            }

            if (dataPointsParam[i] < 0) {
                dataPointsParam[i] = 0;
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        log.info("Połączenie zamknięte: ", System.currentTimeMillis());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);

        Gson gsonBuilder = new GsonBuilder().create();

        getRandomData();

        String jsonFromJavaArrayList = gsonBuilder.toJson(dataPointsParam);

        session.sendMessage(new TextMessage(jsonFromJavaArrayList));

        log.info("Komuinkat wysłany {}", System.currentTimeMillis());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        log.info("Połączenie otwarto: {}", System.currentTimeMillis());
    }

    public void getRandomData() {
        for (int i = 0; i < dataSize; i++) {
            dataPointsParam[i] = dataPointsParam[i] + 2 - randomizer.nextInt(5);

            if (dataPointsParam[i] > 100) {
                dataPointsParam[i] = 100;
            }

            if (dataPointsParam[i] < 0) {
                dataPointsParam[i] = 0;
            }
        }
    }
}
