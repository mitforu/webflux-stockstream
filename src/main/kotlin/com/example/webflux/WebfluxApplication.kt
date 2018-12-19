package com.example.webflux

import com.example.webflux.Stock.Stock
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

@SpringBootApplication
@Configuration
class WebfluxApplication

fun test(value : Int) = ServerResponse.ok().syncBody("test $value")

fun sayHello() = ServerResponse.ok().syncBody("Hello")


fun handleSave(request: ServerRequest) : Mono<ServerResponse>{
    return ServerResponse.ok().body(request.bodyToMono(Stock::class.java))
}

fun main(args: Array<String>) {

    runApplication<WebfluxApplication>(*args)
}

@Configuration
class RouterConfiguration{
    @Bean
    fun route() = router {
        GET("/route") { _ -> ServerResponse.ok().syncBody(arrayOf(1, 2, 3)) }
        GET("/hello").invoke { sayHello() }
        GET("/reactive"){
            ServerResponse.ok().body(Mono.just("Hello"))
        }
        GET("/test").nest {
            GET("/1").invoke { test(1) }
            GET("/2").invoke { test(2) }
        }
        POST("/test").invoke { it: ServerRequest -> handleSave(it)}
    }
}
