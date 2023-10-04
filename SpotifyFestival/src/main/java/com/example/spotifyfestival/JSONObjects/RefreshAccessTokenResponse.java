package com.example.spotifyfestival.JSONObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RefreshAccessTokenResponse {

    @JsonProperty("scope")
    private String refreshedScope;

    @JsonProperty("access_token")
    private String refreshedAccessToken;

    @JsonProperty("token_type")
    private String refreshedTokenType;

    @JsonProperty("expires_in")
    private int refreshedExpiresIn;

    public void setRefreshedAccessToken(String refreshedAccessToken) {
        this.refreshedAccessToken = refreshedAccessToken;
    }

    public void setRefreshedTokenType(String refreshedTokenType) {
        this.refreshedTokenType = refreshedTokenType;
    }

    public void setRefreshedExpiresIn(int refreshedExpiresIn) {
        this.refreshedExpiresIn = refreshedExpiresIn;
    }

    public void setRefreshedScope(String refreshedScope) {
        this.refreshedScope = refreshedScope;
    }

    public String getRefreshedAccessToken() {
        return refreshedAccessToken;
    }

    public String getRefreshedTokenType() {
        return refreshedTokenType;
    }

    public int getRefreshedExpiresIn() {
        return refreshedExpiresIn;
    }

    public String getRefreshedScope() {
        return refreshedScope;
    }

}

