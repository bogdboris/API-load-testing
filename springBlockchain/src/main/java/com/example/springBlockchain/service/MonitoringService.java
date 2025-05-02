package com.example.springBlockchain.service;

import com.example.springBlockchain.model.ApiResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Service
public class MonitoringService {
    private final RestTemplate restTemplate = new RestTemplate();

    public ApiResponse checkApi(String url, String key) {
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
                        url, HttpMethod.GET, entity, String.class
                );
                long duration = System.currentTimeMillis() - startTime;
                return new ApiResponse(response.getBody(), response.getStatusCodeValue(), duration);
            }
        } catch (Exception e) {
            return new ApiResponse("Ошибка при вызове API: " + e.getMessage(), 500); // 500 = Internal Server Error
        }
    }
}
