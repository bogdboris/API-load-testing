package com.example.springBlockchain.service;

import java.util.concurrent.*;
import com.example.springBlockchain.service.MonitoringService;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class MonitoringSchedulerService {

    private ScheduledExecutorService executor;
    private LoadTesterService loadtesterservice;

    MonitoringService monitoringservice = new MonitoringService();

    public MonitoringSchedulerService(MonitoringService monitoringservice){
        this.monitoringservice = monitoringservice;
    }

    public void StartApiLoop(String url, String key, int threads, HttpMethod method){

        if (threads == 0 || threads == 1){
            executor = Executors.newSingleThreadScheduledExecutor();
            executor.scheduleAtFixedRate(() -> {
                monitoringservice.checkApi(url, key, method);
            }, 0, 1, TimeUnit.SECONDS);
        }
        else {
            executor = Executors.newSingleThreadScheduledExecutor();
            executor.scheduleAtFixedRate(() -> {
                monitoringservice.checkApi(url, key, method);
            }, 0, 1, TimeUnit.SECONDS);
        }
    }

    public void EndApiLoop(){
        if (executor != null) {
            executor.shutdown();
        }
    }
}


