package com.neueda.url.controller;

import com.neueda.url.model.UrlRequest;
import com.neueda.url.model.UrlResponse;
import com.neueda.url.model.UrlStatistics;
import com.neueda.url.service.UrlAliasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/url")
public class UrlAliasController {

    @Autowired
    private UrlAliasService urlAliasService;

    @PostMapping("/short")
    public ResponseEntity<UrlResponse> createShortURL(@RequestBody UrlRequest urlRequest) {
        UrlResponse urlResponse = urlAliasService.saveUrlAlias(urlRequest.getOriginalUrl());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, urlResponse.getShortUrl())
                .body(urlResponse);
    }

    @GetMapping("/orig/{code}")
    public ResponseEntity<Object> getUrlAlias(@PathVariable String code){
        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .header(HttpHeaders.LOCATION, urlAliasService.getOriginalUrlByCode(code))
                .build();
    }

    @GetMapping("/details/{code}")
    public ResponseEntity<UrlStatistics> getUrlStatistics(@PathVariable String code) {
        UrlStatistics urlStatistics = urlAliasService.getUrlStatistics(code);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, urlStatistics.getShortUrl())
                .body(urlStatistics);
    }

}
