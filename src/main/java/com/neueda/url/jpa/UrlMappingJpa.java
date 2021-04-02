package com.neueda.url.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "URL_MAPPING")
public class UrlMappingJpa {

    @Id
    @GeneratedValue
    private long urlMappingId;
    private String shortUrl;
    private String code;
    private String originalUrl;
    private long hitCount;
    private Date createdOn;
    private Date lastHitOn;

    public long getUrlMappingId() {
        return urlMappingId;
    }

    public void setUrlMappingId(long urlMappingId) {
        this.urlMappingId = urlMappingId;
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

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public long getHitCount() {
        return hitCount;
    }

    public void setHitCount(long hitCount) {
        this.hitCount = hitCount;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getLastHitOn() {
        return lastHitOn;
    }

    public void setLastHitOn(Date lastHitOn) {
        this.lastHitOn = lastHitOn;
    }
}
