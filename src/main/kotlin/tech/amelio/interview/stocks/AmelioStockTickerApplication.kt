package tech.amelio.interview.stocks

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import tech.amelio.interview.stocks.logic.*

@SpringBootApplication
class AmelioStockTickerApplication {

    @Bean
    fun stockPriceRepository(): StockPriceRepository {
        return StockPriceRepository((1..10).map { "stock-$it" }.toSet())
    }

    @Bean
    fun stockPriceQueue(
        stockPriceRe: StockPriceRepository,
        priceAggregator: StockPriceAggregator
    ): SimpleQueue<StockTick> {
        val queue = SimpleQueue<StockTick>()
        queue.addEventListener(stockPriceRe)
        queue.addEventListener(priceAggregator)
        return queue
    }


    @Bean
    @Profile("!test")
    fun initRmtPlacementCache(priceTicker: PriceTicker): CommandLineRunner {
        return CommandLineRunner { priceTicker.startTicker() }
    }
}

fun main(args: Array<String>) {
    runApplication<AmelioStockTickerApplication>(*args)
}
