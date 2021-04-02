package com.neueda.url.service;

import com.neueda.url.exception.BadRequestException;
import com.neueda.url.exception.NotFoundException;
import com.neueda.url.exception.UrlCreationException;
import com.neueda.url.generator.RandomUrlCodeGenerator;
import com.neueda.url.jpa.UrlMappingJpa;
import com.neueda.url.model.UrlResponse;
import com.neueda.url.model.UrlStatistics;
import com.neueda.url.repository.UrlAliasRepository;
import com.neueda.url.transformer.UrlTransformer;
import com.neueda.url.utils.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class UrlAliasServiceImplTest {

    @InjectMocks
    private UrlAliasServiceImpl urlAliasServiceImpl;

    @Mock
    private UrlAliasRepository urlAliasRepository;

    @Mock
    private UrlTransformer urlTransformer;

    @Mock
    private DateUtils dateUtils;

    @Mock
    private RandomUrlCodeGenerator randomUrlCodeGenerator;

    @Before
    public void setup(){

    }

    @Test
    public void testSaveUrlAlias() {
        UrlMappingJpa urlMappingJpa = getUrlMappingJpa();
        doReturn(null).when(urlAliasRepository).getShortUrlByOriginalUrl("https://originalurl.com");
        doReturn(urlMappingJpa).when(urlAliasRepository).save(urlMappingJpa);
        doReturn(urlMappingJpa).when(urlTransformer).getUrlMappingJpa(any());
        doReturn(getDate()).when(dateUtils).getCurrentDateTime();
        doReturn("code1234").when(randomUrlCodeGenerator).getRandomUrlCode();
        UrlResponse expectedResponse = urlAliasServiceImpl.saveUrlAlias("https://originalurl.com");
        assertEquals(expectedResponse.getOriginalUrl(),"https://originalurl.com");
        assertEquals(expectedResponse.getCode(),"code1234");
    }

    @Test(expected = BadRequestException.class)
    public void testSaveUrlAliasBlankUrl() {
        urlAliasServiceImpl.saveUrlAlias("");
    }

    @Test(expected = BadRequestException.class)
    public void testSaveUrlAliasInvalidUrl() {
        urlAliasServiceImpl.saveUrlAlias("abc");
    }

    @Test(expected = UrlCreationException.class)
    public void testSaveUrlAliasWhenUrlCreationException() {
        UrlMappingJpa urlMappingJpa = getUrlMappingJpa();
        UrlResponse urlResponse= getUrlResponse();
        doReturn(null).when(urlAliasRepository).getShortUrlByOriginalUrl("https://originalurl.com");
        doReturn("code1234").when(randomUrlCodeGenerator).getRandomUrlCode();
        urlAliasServiceImpl.saveUrlAlias("https://originalurl.com");
    }

    @Test
    public void testSaveUrlAliasExistingJpa() {
        UrlMappingJpa urlMappingJpa = getUrlMappingJpa();
        UrlResponse urlResponse= getUrlResponse();
        doReturn(urlMappingJpa).when(urlAliasRepository).getShortUrlByOriginalUrl("https://originalurl.com");
        doReturn(urlResponse).when(urlTransformer).getUrlResponse(urlMappingJpa);
        UrlResponse expectedResponse = urlAliasServiceImpl.saveUrlAlias("https://originalurl.com");
        assertEquals(expectedResponse.getOriginalUrl(),"https://originalurl.com");
        assertEquals(expectedResponse.getShortUrl(),"https://shorturl.com");
        assertEquals(expectedResponse.getCode(),"code1234");
    }

    @Test
    public void testGetOriginalUrlByCode() {
        UrlMappingJpa urlMappingJpa = getUrlMappingJpa();
        doReturn(urlMappingJpa).when(urlAliasRepository).getOriginalUrlByCode("code1234");
        doReturn(urlMappingJpa).when(urlAliasRepository).save(urlMappingJpa);
        doReturn(getDate()).when(dateUtils).getCurrentDateTime();
        String expectedResponse = urlAliasServiceImpl.getOriginalUrlByCode("code1234");
        assertEquals(expectedResponse,"https://originalurl.com");
    }

    @Test
    public void testGetUrlStatistics() {
        UrlMappingJpa urlMappingJpa = getUrlMappingJpa();
        UrlStatistics urlStatistics = getUrlStatistics();
        doReturn(urlMappingJpa).when(urlAliasRepository).getOriginalUrlByCode("code1234");
        doReturn(urlStatistics).when(urlTransformer).getUrlStatistics(urlMappingJpa);
        UrlStatistics expectedResponse = urlAliasServiceImpl.getUrlStatistics("code1234");
        assertEquals(expectedResponse.getCode(), "code1234");
        assertEquals(expectedResponse.getOriginalUrl(), "https://originalurl.com");
        assertEquals(expectedResponse.getShortUrl(), "https://shorturl.com");
        assertEquals(expectedResponse.getHitCount(), 1);
        assertEquals(expectedResponse.getCreatedOn(), "02/04/2021");
        assertEquals(expectedResponse.getLastHitOn(), "02/04/2021");
    }

    @Test
    public void testGetUrlStatisticsWhenNullJpa() {
        UrlStatistics urlStatistics = getUrlStatistics();
        doReturn(null).when(urlAliasRepository).getOriginalUrlByCode("code1234");
        try{
            urlAliasServiceImpl.getUrlStatistics("code1234");
        } catch(NotFoundException e){
            assertEquals(e.getMessage(), "No Data found for given Code : code1234");
        }
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

    private UrlStatistics getUrlStatistics(){
        UrlStatistics urlStatistics = new UrlStatistics();
        urlStatistics.setLastHitOn("02/04/2021");
        urlStatistics.setShortUrl("https://shorturl.com");
        urlStatistics.setHitCount(1);
        urlStatistics.setCreatedOn("02/04/2021");
        urlStatistics.setOriginalUrl("https://originalurl.com");
        urlStatistics.setCode("code1234");
        return urlStatistics;
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
