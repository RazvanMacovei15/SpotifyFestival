package com.example.spotifyfestival.helperObsLis;

import javafx.event.Event;
import javafx.event.EventType;

public class AuthFlowCompletedEvent extends Event {

    public static final EventType<AuthFlowCompletedEvent> AUTH_FLOW_COMPLETED =
            new EventType<>(Event.ANY, "AUTH_FLOW_COMPLETED");

    private String accessToken;

    public AuthFlowCompletedEvent(String accessToken) {
        super(AUTH_FLOW_COMPLETED);
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
