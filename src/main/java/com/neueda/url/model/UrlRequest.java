package com.neueda.url.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UrlRequest {

    @JsonProperty(value="originalUrl", access=JsonProperty.Access.READ_WRITE)
    private String originalUrl;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
