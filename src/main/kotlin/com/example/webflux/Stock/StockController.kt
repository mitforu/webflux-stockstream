package com.example.webflux.Stock

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.zankowski.iextrading4j.client.IEXTradingClient
import pl.zankowski.iextrading4j.client.rest.request.stocks.QuoteRequestBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.toFlux
import java.math.BigDecimal
import java.time.Duration

@RestController
class StockController{

    @CrossOrigin
    @GetMapping(value = ["/stock"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getFANGStockPrice() : Flux<List<Stock>> {

        return Flux.interval(Duration.ofSeconds(2)).flatMap {
            i -> Flux.fromIterable(listOf("FB","AAPL","NFLX","GOOGL"))
                .toFlux()
                .flatMap{ value -> getStockPrice(value) }
                .buffer(4)
        }
    }

    private fun getStockPrice(symbol: String) : Flux<Stock>{
        return listOf(symbol)
                .toFlux()
                .subscribeOn(reactor.core.scheduler.Schedulers.elastic())
                .map { it ->  getLatestPrice(it)}
    }

    private fun getLatestPrice(symbol: String) : Stock{
        val iexTradingClient = IEXTradingClient.create()
        println("Getting Stock Price for $symbol on ${Thread.currentThread().name}")
        val quote =  iexTradingClient.executeRequest(QuoteRequestBuilder().withSymbol(symbol).build())

        return Stock(symbol, quote.iexRealtimePrice)
    }
}

data class Stock(
        val name : String,
        val price: BigDecimal?
)