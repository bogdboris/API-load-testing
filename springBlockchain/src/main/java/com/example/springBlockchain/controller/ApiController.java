package com.example.springBlockchain.controller;

import com.example.springBlockchain.model.ApiResponse;
import com.example.springBlockchain.model.LoadTestSummary;
import com.example.springBlockchain.service.LoadTesterService;
import com.example.springBlockchain.service.MonitoringSchedulerService;
import com.example.springBlockchain.service.MonitoringService;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ApiController {
    private final MonitoringService monitoringService;
    private final MonitoringSchedulerService monitoringschedulerservice;
    private final LoadTesterService loadtesterservice;

    public ApiController(MonitoringService monitoringService, MonitoringSchedulerService monitoringschedulerservice, LoadTesterService loadtesterservice) {
        this.monitoringService = monitoringService;
        this.monitoringschedulerservice = monitoringschedulerservice;
        this.loadtesterservice = loadtesterservice;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("request", new ApiRequest());
        return "index";
    }

    @PostMapping("/check")
    public ResponseEntity<ApiResponse> checkApi(@ModelAttribute ApiRequest request) {
        ApiResponse result = monitoringService.checkApi(request.getUrl(), request.getKey(), request.getMethod());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/loop")
    public ResponseEntity<String> StartApiLoop(@ModelAttribute ApiRequest request){
        if (request.getThreads() > 1){
            monitoringschedulerservice.StartApiLoop(request.getUrl(), request.getKey(), request.getThreads(), request.getMethod());
        }
        else {monitoringschedulerservice.StartApiLoop(request.getUrl(), request.getKey(), 0, request.getMethod());}

        return ResponseEntity.ok("loop started.");
    }

    @PostMapping("/treadTest")
    public ResponseEntity<LoadTestSummary> startThreadTest(@ModelAttribute ApiRequest request) {
        int threads = request.getThreads() > 0 ? request.getThreads() : 1;
        LoadTestSummary summary = loadtesterservice.LoadTester(request.getKey(), request.getUrl(), threads, request.getMethod());
        return ResponseEntity.ok(summary);
    }



    public static class ApiRequest {
        private String url;
        private String key;
        private int threads;
        private HttpMethod method;

        public HttpMethod getMethod(){
            return method;
        }

        public void setMethod(HttpMethod method) {

            this.method = method;
        }

        public String getUrl() {

            return url;
        }

        public void setUrl(String url) {

            this.url = url;
        }

        public String getKey() {

            return key;
        }

        public void setKey(String key) {

            this.key = key;
        }
        public int getThreads() {

            return threads;
        }

        public void setThreads(int threads){

            this.threads = threads;
        }

    }
}