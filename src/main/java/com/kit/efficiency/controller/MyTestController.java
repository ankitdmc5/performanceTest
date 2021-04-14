package com.kit.efficiency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class MyTestController {

    private final RestTemplate restTemplate;
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Autowired
    public MyTestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }

    @GetMapping("/test")
    public String test() throws InterruptedException {
        Thread.sleep((int)((Math.random() + 1)*200));
        /*System.out.println(Thread.currentThread());
        final Runtime runtime = Runtime.getRuntime();
        System.out.println("freeMemory: " + (runtime.freeMemory() / (1024*1024)) + "MB");
        System.out.println("totalMemory: " + (runtime.totalMemory() / (1024*1024)) + "MB");
        System.out.println("maxMemory: " + (runtime.maxMemory() / (1024*1024)) + "MB");*/
        atomicInteger.getAndIncrement();
        return "YOLO";
    }

    @GetMapping("/testPerformance")
    public AtomicInteger testPerformance() throws InterruptedException {
        atomicInteger.set(1);
        List<Thread> threadList = new ArrayList<>(100);
        for (int i = 0; i< 1500; i++){
            //String s = new String(new char[100000000]).replace('\0','a');
            Thread t = new Thread(() -> {
                this.restTemplate
                        .exchange("http://localhost:9000/test", HttpMethod.GET, HttpEntity.EMPTY, String.class);
            });
            threadList.add(t);
            System.out.println(t);
        }
        //Thread.sleep(7000);
        for (Thread t:
             threadList) {
            t.start();
        }
        ResponseEntity<String> temp = this.restTemplate
                .exchange("http://localhost:9000/test", HttpMethod.GET, HttpEntity.EMPTY, String.class);
        return atomicInteger;
    }

}
