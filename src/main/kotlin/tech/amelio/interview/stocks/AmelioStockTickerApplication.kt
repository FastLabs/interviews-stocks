package tech.amelio.interview.stocks

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AmelioStockTickerApplication

fun main(args: Array<String>) {
    runApplication<AmelioStockTickerApplication>(*args)
}
