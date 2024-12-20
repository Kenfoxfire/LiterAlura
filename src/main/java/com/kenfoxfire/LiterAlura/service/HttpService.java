package com.kenfoxfire.LiterAlura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class HttpService {


    private final HttpClient CLIENT = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
    @Value("${api.url}")
    private String API_URL;


    public String findLibroByTitle(String title) {
        String encodedTitle = encodeTitle(title);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "books/?search=" + encodedTitle))
                .build();
        try {
            HttpResponse<String> response = CLIENT
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al enviar la solicitud HTTP: " + e.getMessage(), e);
        }

    }

    private String encodeTitle(String title) {
        return URLEncoder.encode(title, StandardCharsets.UTF_8);
    }

}
