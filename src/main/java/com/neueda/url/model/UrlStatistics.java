package com.neueda.url.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UrlStatistics {

    @JsonProperty(value="originalUrl", access=JsonProperty.Access.READ_WRITE)
    private String originalUrl;

    @JsonProperty(value="shortUrl", access=JsonProperty.Access.READ_WRITE)
    private String shortUrl;

    @JsonProperty(value="code", access=JsonProperty.Access.READ_WRITE)
    private String code;

    @JsonProperty(value="hitCount", access=JsonProperty.Access.READ_WRITE)
    private long hitCount;

    @JsonProperty(value="createdOn", access=JsonProperty.Access.READ_WRITE)
    private String createdOn;

    @JsonProperty(value="lastHitOn", access=JsonProperty.Access.READ_WRITE)
    private String lastHitOn;

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

    public long getHitCount() {
        return hitCount;
    }

    public void setHitCount(long hitCount) {
        this.hitCount = hitCount;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getLastHitOn() {
        return lastHitOn;
    }

    public void setLastHitOn(String lastHitOn) {
        this.lastHitOn = lastHitOn;
    }
}
