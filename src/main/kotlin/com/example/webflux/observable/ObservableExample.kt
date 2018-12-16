package com.example.webflux.observable

import io.reactivex.Observable
import io.reactivex.Observable.merge
import io.reactivex.Observable.zip
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers


fun main(args: Array<String>) {
    val list = listOf("Alpha", "Beta", "Gamma", "Delta", "Epsilon")

    /**
     *  creating and subscribing to and observable
     */

//    list.toObservable()
//            .subscribeBy(
//                    onNext = { println(it)},
//                    onError = { it.printStackTrace() },
//                    onComplete = { println("Done!") }
//            )

    /**
     *  onNext throws and error
     */

//    list.toObservable()
//            .subscribeBy(
//                    onNext = {
//                        println(it)
//                        throw Exception("Something went wrong...")
//                    },
//                    onError = {
//                        println(it.message)
//                        it.printStackTrace()
//                    },
//                    onComplete = { println("Done!") }
//            )


    /**
     *  Filter items whose length is less than 4
     */

//    println("with length > 4")
//    list.toObservable()
//            .filter { it -> it.length > 4 }
//            .subscribe { it -> println(it) }


    /**
     *  Below two process with happen on two different thread
     */


//    println("Current Thread name " + Thread.currentThread().name)
//    list.toObservable()
//            .subscribeOn(Schedulers.io())
//            .subscribe {
//                println("Thread Running on for first" + Thread.currentThread().name)
//                println(it)
//            }
//
//    list.toObservable()
//            .subscribeOn(Schedulers.io())
//            .subscribe {
//                println("Thread Running on for second" + Thread.currentThread().name)
//                println(it)
//            }
//


    /**
     *  Reduce Operator
     */

//    list.toObservable()
//            .map { it.length }
//            .reduce { t1: Int, t2: Int -> t1+t2 }
//            .subscribe { println("total length $it") }
//
//


    /**
     *  Merge Two Streams and emit when both stream emit item
     */

//    zip(someAsyncCall(), anotherAsyncCall(), BiFunction<String, String , String> { t1, t2 -> "$t1 $t2" }).blockingSubscribe { println(it) }

    Thread.currentThread().join()
}



fun someAsyncCall() : Observable<String>{
    Thread.sleep(2000)
    return Observable.just("Hello")
}

fun anotherAsyncCall() : Observable<String>{
    Thread.sleep(6000)
    return Observable.just("World")
}