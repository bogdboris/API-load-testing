package com.example.springBlockchain.service;


import com.example.springBlockchain.model.ApiResponse;
import com.example.springBlockchain.model.LoadTestResult;
import com.example.springBlockchain.model.LoadTestSummary;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class LoadTesterService {


    MonitoringService monitoringservice = new MonitoringService();
    private ScheduledExecutorService executor;

    public LoadTesterService(MonitoringService monitoringservice) {
        this.monitoringservice = monitoringservice;
    }

    public LoadTestSummary LoadTester(String key, String url, int threads, HttpMethod method) {


        ExecutorService executor = Executors.newFixedThreadPool(threads);

        List<Future<ApiResponse>> futures = new ArrayList<>();
        List<LoadTestResult> results = new ArrayList<>();

        for (int i = 0; i < threads; i++) {
            futures.add(executor.submit(() -> monitoringservice.checkApi(url, key, method)));
        }

        int totalSuccess = 0;
        int totalFailure = 0;
        long totalDuration = 0;

        for (Future<ApiResponse> future : futures) {
            try {
                ApiResponse apiResponse = future.get();

                LoadTestResult result = new LoadTestResult();
                result.setBody(apiResponse.getBody());
                result.setDuration(apiResponse.getDuration());
                result.setStatusCode(apiResponse.getStatusCode());

                if (apiResponse.getStatusCode() >= 200 && apiResponse.getStatusCode() < 300) {
                    result.setSuccessCount(1);
                    result.setFailureCount(0);
                    totalSuccess++;
                } else {
                    result.setMistakes("Ошибка API: Статус " + apiResponse.getStatusCode() + " - " + apiResponse.getBody());
                    result.setSuccessCount(0);
                    result.setFailureCount(1);
                    totalFailure++;
                }

                totalDuration += apiResponse.getDuration();
                results.add(result);

            } catch (ExecutionException e) {
                LoadTestResult errorResult = new LoadTestResult();
                errorResult.setSuccessCount(0);
                errorResult.setFailureCount(1);
                errorResult.setMistakes("Ошибка при выполнении задачи: " + e.getCause().getMessage());
                results.add(errorResult);
                totalFailure++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Поток прерван: " + e.getMessage());
            }

        }


        LoadTestSummary summary = new LoadTestSummary();

        summary.setTotalSuccessCount(totalSuccess);
        summary.setTotalFailureCount(totalFailure);
        summary.setAverageDuration(results.isEmpty() ? 0 : (double) totalDuration / results.size());
        summary.setAllresults(results);

        return summary;
    }
}
