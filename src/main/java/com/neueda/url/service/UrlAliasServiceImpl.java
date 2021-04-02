package com.neueda.url.service;

import com.neueda.url.exception.BadRequestException;
import com.neueda.url.exception.NotFoundException;
import com.neueda.url.exception.UrlCreationException;
import com.neueda.url.generator.RandomUrlCodeGenerator;
import com.neueda.url.jpa.UrlMappingJpa;
import com.neueda.url.message.Message;
import com.neueda.url.model.UrlResponse;
import com.neueda.url.model.UrlStatistics;
import com.neueda.url.repository.UrlAliasRepository;
import com.neueda.url.transformer.UrlTransformer;
import com.neueda.url.utils.DateUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

@Service("urlAliasService")
public class UrlAliasServiceImpl implements UrlAliasService {

    @Autowired
    UrlAliasRepository urlAliasRepository;

    @Autowired
    UrlTransformer urlTransformer;

    @Autowired
    RandomUrlCodeGenerator randomUrlCodeGenerator;

    @Autowired
    private DateUtils dateUtils;

    @Value("${server.hostname}")
    private String host;

    @Value("${server.contextPath}")
    private String contextPath;

    @Value("${server.port}")
    private String port;

    @Override
    public UrlResponse saveUrlAlias(String originalUrl) {
        if (!StringUtils.hasText(originalUrl)) {
            throw new BadRequestException(Message.ErrorMessage.EMPTY_ORIGINAL_URL);
        }
        try {
            new URL(originalUrl);
        } catch (MalformedURLException e) {
            throw new BadRequestException(
                    String.format("%s : %s", Message.ErrorMessage.INVALID_ORIGINAL_URL, originalUrl));
        }
        UrlResponse UrlResponse = null;
        try {
            UrlMappingJpa urlMappingJpa = urlAliasRepository.getShortUrlByOriginalUrl(originalUrl);
            if (Objects.nonNull(urlMappingJpa) && Strings.isNotBlank(urlMappingJpa.getShortUrl())){
                return urlTransformer.getUrlResponse(urlMappingJpa);
            }
            UrlResponse = new UrlResponse();
            String code = getUniqueCode(originalUrl);
            UrlResponse.setOriginalUrl(originalUrl);
            UrlResponse.setShortUrl(String.format("%s:%s%s/%s", host, port, contextPath, code));
            UrlResponse.setCode(code);
            urlMappingJpa = urlTransformer.getUrlMappingJpa(UrlResponse);
            urlMappingJpa.setHitCount(0);
            urlMappingJpa.setCreatedOn(dateUtils.getCurrentDateTime());
            urlAliasRepository.save(urlMappingJpa);
        } catch (Exception ex){
            throw new UrlCreationException(Message.ErrorMessage.ERROR_CREATING_URL, 500);
        }
        return UrlResponse;
    }

    @Override
    public String getOriginalUrlByCode(String code) {
        UrlMappingJpa urlMappingJpa = urlAliasRepository.getOriginalUrlByCode(code);
        long count = urlMappingJpa.getHitCount();
        urlMappingJpa.setHitCount(count+1);
        urlMappingJpa.setLastHitOn(dateUtils.getCurrentDateTime());
        urlAliasRepository.save(urlMappingJpa);
        return urlMappingJpa.getOriginalUrl();
    }

    @Override
    public UrlStatistics getUrlStatistics(String code) {
        UrlMappingJpa urlMappingJpa = urlAliasRepository.getOriginalUrlByCode(code);
        if (Objects.isNull(urlMappingJpa)) {
            throw new NotFoundException(
                    String.format("%s : %s", Message.ErrorMessage.CODE_NOT_FOUND, code));
        }
        return urlTransformer.getUrlStatistics(urlMappingJpa);
    }

    private String getUniqueCode(String originalUrl) {
        return randomUrlCodeGenerator.getRandomUrlCode();
    }
}
