package com.woowacourse.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.http.HttpStatus;

class ConcurrentHttpRequester {

    private final ExecutorService executorService;
    private final CountDownLatch start;
    private final CountDownLatch end;
    private final AtomicInteger successUser;
    private final AtomicInteger failUser;

    public ConcurrentHttpRequester(int poolSize) {
        this.executorService = Executors.newFixedThreadPool(poolSize);
        this.start = new CountDownLatch(poolSize);
        this.end = new CountDownLatch(poolSize);
        this.successUser = new AtomicInteger(0);
        this.failUser = new AtomicInteger(0);
    }

    public void submit(HttpRequestExecutor executor) {
        executorService.submit(() -> {
            try {
                start.countDown();
                start.await();
                HttpStatus status = executor.execute();
                if (status.is2xxSuccessful()) {
                    successUser.incrementAndGet();
                } else {
                    failUser.incrementAndGet();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                end.countDown();
            }
        });
    }

    public void await() {
        try {
            end.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getSuccessUser() {
        return successUser.get();
    }

    public int getFailUser() {
        return failUser.get();
    }
}
