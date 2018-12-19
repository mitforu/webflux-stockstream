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
        /**
         * Java Future API Added In java 5
         */
        System.out.println("calculating square for 5");
        Future<Integer> result = calculate(5);
        System.out.println(result.get());

        /**
         * CompletableFuture is used for asynchronous programming in Java.
         * Run Async does not return any value
         *
         */
        completableFutureRunAsync().get();

        /**
         * CompletableFuture is used for asynchronous programming in Java.
         * Supply Async returns a value
         *
         */
        System.out.println(completableFutureSupplyAsync().get());


        /**
         * CompletableFuture is used for asynchronous programming in Java.
         * Chaining call together
         *
         */
        System.out.println(completableFutureSupplyAsyncThenApply().get());

        /**
         * Google ListenableFuture which allows to pass callback
         * http://callbackhell.com/
         */
        googleListenableFutureExample();

        /**
         * Callback Hell
         */
        callbackHell();

    }

    public Future<Integer> calculate(Integer input) {
        return executor.submit(() -> {
            Thread.sleep(1000);
            return input * input;
        });
    }

    public Future<Void> completableFutureRunAsync() {
        return CompletableFuture.runAsync(() -> {
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

    public CompletableFuture completableFutureSupplyAsyncThenApply() {
        CompletableFuture hello = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            return "hello";
        });

        CompletableFuture result = hello.thenApply(data -> data + " world");

        result.whenComplete((o1, o2) -> {
            System.out.println(" completed");
        });

        return result;
    }

    public void googleListenableFutureExample() {
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
                System.out.println("Completed with callback " + result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("failure " + t.getMessage());
            }
        });

    }



    public void callbackHell() {
        ListenableFuture<Person> person1Future = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool()).submit(() -> {
            return new Person(1,"Person");
        });

        Futures.addCallback(person1Future, new FutureCallback<Person>() {
            @Override
            public void onSuccess(Person person1) {
                System.out.println("Completed with callback " + person1);

                ListenableFuture<Person> person2Future = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool()).submit(() -> {
                    return new Person(2,"Person2");
                });

                Futures.addCallback(person2Future, new FutureCallback<Person>() {
                    @Override
                    public void onSuccess(Person person2) {
                        System.out.println("Completed with callback " + person2);
                        ListenableFuture<Person> person3Future = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool()).submit(() -> {
                            return new Person(3,"Person3");
                        });

                        Futures.addCallback(person3Future, new FutureCallback<Person>() {
                            @Override
                            public void onSuccess(Person person3) {
                                System.out.println("Completed with callback " + person3);
                                ListenableFuture<Person> person4Future = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool()).submit(() -> {
                                    return new Person(4,"Person4");
                                });
                                Futures.addCallback(person4Future, new FutureCallback<Person>() {
                                    @Override
                                    public void onSuccess(Person person4) {
                                        System.out.println("Completed with callback " + person4);
                                    }

                                    @Override
                                    public void onFailure(Throwable t) {
                                        System.out.println("failure " + t.getMessage());
                                    }
                                });
                            }
                            @Override
                            public void onFailure(Throwable t) {
                                System.out.println("failure " + t.getMessage());
                            }
                        });
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        System.out.println("failure " + t.getMessage());
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("failure " + t.getMessage());
            }
        });

    }

}
