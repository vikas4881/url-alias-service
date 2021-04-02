package com.neueda.url.transformer;

import com.neueda.url.jpa.UrlMappingJpa;
import com.neueda.url.model.UrlResponse;
import com.neueda.url.model.UrlStatistics;
import com.neueda.url.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UrlTransformerTest {

    @InjectMocks
    private UrlTransformer urlTransformer;

    @Mock
    private DateUtils dateUtils;

    @Test
    public void testGetUrlResponse() {
        UrlMappingJpa urlMappingJpa = getUrlMappingJpa();
        UrlResponse expectedResponse = urlTransformer.getUrlResponse(urlMappingJpa);
        assertEquals(expectedResponse.getCode(), "code1234");
        assertEquals(expectedResponse.getShortUrl(), "https://shorturl.com");
    }

    @Test
    public void testGetUrlMappingJpa() {
        UrlResponse urlResponse= getUrlResponse();
        UrlMappingJpa expectedResponse = urlTransformer.getUrlMappingJpa(urlResponse);
        assertEquals(expectedResponse.getCode(), "code1234");
        assertEquals(expectedResponse.getShortUrl(), "https://shorturl.com");
        assertEquals(expectedResponse.getOriginalUrl(), "https://originalurl.com");
    }

    @Test
    public void testGetUrlStatistics() {
        UrlMappingJpa urlMappingJpa = getUrlMappingJpa();
        UrlStatistics expectedResponse = urlTransformer.getUrlStatistics(urlMappingJpa);
        assertEquals(expectedResponse.getCode(), "code1234");
        assertEquals(expectedResponse.getShortUrl(), "https://shorturl.com");
        assertEquals(expectedResponse.getOriginalUrl(), "https://originalurl.com");
        assertEquals(expectedResponse.getHitCount(), 1);
    }

    private UrlResponse getUrlResponse(){
        UrlResponse urlResponse= new UrlResponse();
        urlResponse.setCode("code1234");
        urlResponse.setShortUrl("https://shorturl.com");
        urlResponse.setOriginalUrl("https://originalurl.com");
        return urlResponse;
    }

    private UrlMappingJpa getUrlMappingJpa(){
        UrlMappingJpa urlMappingJpa = new UrlMappingJpa();
        urlMappingJpa.setUrlMappingId(1);
        urlMappingJpa.setHitCount(1);
        urlMappingJpa.setOriginalUrl("https://originalurl.com");
        urlMappingJpa.setShortUrl("https://shorturl.com");
        urlMappingJpa.setCode("code1234");
        urlMappingJpa.setLastHitOn(getDate());
        urlMappingJpa.setCreatedOn(getDate());
        return urlMappingJpa;
    }

    private Date getDate() {
        String strDate="02/04/2021";
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

}
