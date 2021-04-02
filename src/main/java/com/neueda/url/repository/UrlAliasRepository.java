package com.neueda.url.repository;

import com.neueda.url.jpa.UrlMappingJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlAliasRepository extends JpaRepository<UrlMappingJpa, Long> {
    UrlMappingJpa getShortUrlByOriginalUrl(String originalUrl);
    UrlMappingJpa getOriginalUrlByCode(String code);
}
