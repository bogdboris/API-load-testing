package com.example.springBlockchain.service;

import com.example.springBlockchain.model.ApiResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class MonitoringService {
    private final RestTemplate restTemplate = new RestTemplate();

    public ApiResponse checkApi(String url, String key, HttpMethod method) {
        System.out.println(method);
        try {
            long startTime = System.currentTimeMillis();

            if (key == null || key.isEmpty()) {
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                long duration = System.currentTimeMillis() - startTime;
                return new ApiResponse(response.getBody(), response.getStatusCodeValue(), duration);
            } else {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + key);
                headers.set("Accept", "application/json");

                HttpEntity<String> entity = new HttpEntity<>(headers);
                ResponseEntity<String> response = restTemplate.exchange(
                        url, method, entity, String.class
                );
                long duration = System.currentTimeMillis() - startTime;
                return new ApiResponse(response.getBody(), response.getStatusCodeValue(), duration);
            }
        } catch (HttpClientErrorException e) {
        return new ApiResponse("Ошибка клиента: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e.getStatusCode().value());
        } catch (HttpServerErrorException e) {
        return new ApiResponse("Ошибка сервера: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e.getStatusCode().value());
        } catch (RestClientException e) {
        return new ApiResponse("Ошибка при вызове API: " + e.getMessage(), 502); // 502 = Bad Gateway
        } catch (Exception e) {
        return new ApiResponse("Неизвестная ошибка: " + e.getMessage(), 500);
        }
    }
}
