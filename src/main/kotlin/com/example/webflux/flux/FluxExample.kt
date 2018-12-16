package com.example.webflux.flux

import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers


fun main(args: Array<String>) {


    /**
     *  Mono Emits one single item
     */
//    val person: Mono<Person> = Mono.just(Person(id = 1, name = "person1"))
//    person.subscribe { it -> println(it) }


    /**
     *  Flux emits more than one item. Its like a list
     */
//    val persons: Flux<Person> =  Flux.just(Person(id = 1, name = "person1"), Person(id = 2, name = "person2"))
//    persons.subscribe { println(it) }
//
//    println()
//    println("getting person living in chicago and nyc..............")


    /**
     *  FlatMap vs Map Example.
     *  FlatMap coverts emitted item to observable and flattens the result
     */

//    println("getting person living in chicago and nyc..............MAP")
//    Flux.just("Chicago","NYC")
//            .map { city -> getPerson(city) }
//            .subscribe {
//                persons: Flux<Person> ->  persons.subscribe { person -> println(person) }
//            }
//
//
//
//    println("getting person living in chicago and nyc..............FLATMAP")
//    Flux.just("Chicago","NYC")
//            .flatMap { city -> getPerson(city) }
//            .subscribe { person -> println(person) }
//

    /**
     *  FlatMap parallel processing. Inner subscribe is on separate thread
     *  FlatMap coverts emitted item to observable and flattens the result
     */

//    Flux.range(1,5)
//            .flatMap { it ->
//                Flux.just(it)
//                    .subscribeOn(Schedulers.elastic())
//                    .map {
//                        println(Thread.currentThread().name)
//                        it*it
//                    }
//            }.subscribe {   println(it) }


    /**
     *  concatMap parallel processing. Inner subscribe is on separate thread
     *  concatMap Maintains the order in which the items are emitted
     */

    Flux.range(1,5)
            .concatMap { it ->
                Flux.just(it)
                        .subscribeOn(Schedulers.elastic())
                        .map {
                            println(Thread.currentThread().name)
                            it*it
                        }
            }.subscribe {   println(it) }


    Thread.currentThread().join()
}


fun  getPerson(city : String?): Flux<Person>{
    Thread.sleep(1000)
   return  if(city == "Chicago"){
        Flux.just(Person(id = 1, name = "person1", city = "Chicago"), Person(id = 2, name = "person2", city = "Chicago"))
    }else if (city == "NYC"){
        Flux.just(Person(id = 3, name = "person3", city = "NYC"), Person(id = 4, name = "person4", city = "NYC"))
    }else{
        Flux.just(Person(id = 3, name = "person5"), Person(id = 4, name = "person6"))
    }
}

data class Person(
        val id : Int,
        val name: String,
        val city: String? = null
)



