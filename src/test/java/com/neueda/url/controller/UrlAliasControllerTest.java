package com.neueda.url.controller;

import com.neueda.url.model.UrlResponse;
import com.neueda.url.service.UrlAliasService;
import com.neueda.url.service.UrlAliasServiceImpl;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import java.lang.reflect.Field;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(UrlAliasController.class)
@AutoConfigureRestDocs(outputDir = "build/snippets")
public class UrlAliasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UrlAliasController urlAliasController;

    @Autowired
    private UrlAliasServiceImpl urlAliasService;

    @Ignore
    @Test
    public void testCreateShortUrl() throws Exception {
        UrlResponse urlResponse = getUrlResponse();
        doReturn(urlResponse).when(urlAliasService).saveUrlAlias("https://originalurl.com");
        Field nbaServiceField = UrlAliasController.class.getDeclaredField("urlAliasService");
        nbaServiceField.setAccessible(true);
        nbaServiceField.set(urlAliasController, urlAliasService);

        RequestBuilder request = RestDocumentationRequestBuilders.post(
                "/url/short").header("Authorization", "Basic dXNlcjpzZWNyZXQ=")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getRequestJsonString())
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(getResponseJsonString()));
    }


    private UrlResponse getUrlResponse() {
        UrlResponse urlResponse= new UrlResponse();
        urlResponse.setCode("code1234");
        urlResponse.setShortUrl("https://shorturl.com");
        urlResponse.setOriginalUrl("https://originalurl.com");
        return urlResponse;
    }

    private String getRequestJsonString() {
        return "{\n" +
                "  \"originalUrl\": \"https://originalurl.com\"\n" +
                "}";
    }

    private String getResponseJsonString() {
        return "{\n" +
                "    \"originalUrl\": \"https://originalurl.com/\",\n" +
                "    \"shortUrl\": \"http://shorturl.com\",\n" +
                "    \"code\": \"code1234\"\n" +
                "}";
    }
}
