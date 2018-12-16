package com.example.webflux.future;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.*;

public class FutureExample {

    private ExecutorService executor
            = Executors.newSingleThreadExecutor();


    public void futureAndCompletableFuture() throws ExecutionException, InterruptedException {
        System.out.println("calculating square for 5");
        Future<Integer> result = new FutureExample().calculate(5);
        System.out.println(result.get());

        completableFutureRunAsync();

        System.out.println(new FutureExample().completableFutureSupplyAsync().get());

        System.out.println(new FutureExample().completableFutureSupplyAsyncThenApple().get());

        new FutureExample().googleListenableFutureExample();
    }

    public Future<Integer> calculate(Integer input) {
        return executor.submit(() -> {
            Thread.sleep(1000);
            return input * input;
        });
    }

    public void completableFutureRunAsync() {
        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            System.out.println("Running async");
        });
    }

    public CompletableFuture<String> completableFutureSupplyAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            return "Async result";
        });
    }

    public CompletableFuture completableFutureSupplyAsyncThenApple() {
        CompletableFuture hello = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            return "hello";
        });

        CompletableFuture result = hello.thenApply(data -> data + " world");

        result.whenComplete((o1,o2) -> System.out.println(" completed"));

        return result;
    }

    public void googleListenableFutureExample(){
        ListenableFuture<String> future = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool()).submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello World!!!!";
        });

        Futures.addCallback(future, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("Completed with callback "+ result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("failure "+ t.getMessage());
            }
        });

    }
}
