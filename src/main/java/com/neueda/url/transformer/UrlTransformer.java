package com.neueda.url.transformer;

import com.neueda.url.jpa.UrlMappingJpa;
import com.neueda.url.model.UrlResponse;
import com.neueda.url.model.UrlStatistics;
import com.neueda.url.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UrlTransformer {

    @Autowired
    private DateUtils dateUtils;

    public UrlResponse getUrlResponse(UrlMappingJpa urlMappingJpa) {
        UrlResponse urlResponse = new UrlResponse();
        urlResponse.setShortUrl(urlMappingJpa.getShortUrl());
        urlResponse.setCode(urlMappingJpa.getCode());
        return urlResponse;
    }

    public UrlMappingJpa getUrlMappingJpa(UrlResponse urlResponse){
        UrlMappingJpa urlMappingJpa = new UrlMappingJpa();
        urlMappingJpa.setCode(urlResponse.getCode());
        urlMappingJpa.setShortUrl(urlResponse.getShortUrl());
        urlMappingJpa.setOriginalUrl(urlResponse.getOriginalUrl());
        return urlMappingJpa;
    }

    public UrlStatistics getUrlStatistics(UrlMappingJpa urlMappingJpa) {
        UrlStatistics urlStatistics = new UrlStatistics();
        urlStatistics.setShortUrl(urlMappingJpa.getShortUrl());
        urlStatistics.setCode(urlMappingJpa.getCode());
        urlStatistics.setOriginalUrl(urlMappingJpa.getOriginalUrl());
        urlStatistics.setHitCount(urlMappingJpa.getHitCount());
        urlStatistics.setCreatedOn(getDateInString(urlMappingJpa.getCreatedOn()));
        urlStatistics.setLastHitOn(getDateInString(urlMappingJpa.getLastHitOn()));
        return urlStatistics;
    }

    private String getDateInString(Date date) {
        return dateUtils.getDateInString(date);
    }
}
