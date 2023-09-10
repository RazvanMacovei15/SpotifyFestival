package com.example.spotifyfestival;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RefreshAccessTokenResponse {


    @JsonProperty("access_token")
    private String refreshedAccessToken;

    @JsonProperty("token_type")
    private String refreshedTokenType;

    @JsonProperty("expires_in")
    private int refreshedExpiresIn;

    @JsonProperty("scope")
    private String refreshedScope;

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

}

