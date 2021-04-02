package com.neueda.url.service;

import com.neueda.url.model.UrlResponse;
import com.neueda.url.model.UrlStatistics;

public interface UrlAliasService {

    UrlResponse saveUrlAlias(String originalUrl);
    String getOriginalUrlByCode(String code);
    UrlStatistics getUrlStatistics(String code);

}
