package com.example.webflux.future


class KotinRx

fun main(args: Array<String>){
    FutureExample().futureAndCompletableFuture()
}

data class Person(
        val id : Int,
        val name : String
)