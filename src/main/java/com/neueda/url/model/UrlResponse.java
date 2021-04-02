package com.neueda.url.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UrlResponse {

    @JsonProperty(value="originalUrl", access=JsonProperty.Access.READ_WRITE)
    private String originalUrl;

    @JsonProperty(value="shortUrl", access=JsonProperty.Access.READ_WRITE)
    private String shortUrl;

    @JsonProperty(value="code", access=JsonProperty.Access.READ_WRITE)
    private String code;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
