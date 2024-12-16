package tech.amelio.interview.stocks.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.amelio.interview.stocks.logic.StockPriceAggregator

@RestController
@RequestMapping("/sum-stocks")
class SumStocksController (private val priceAggregator: StockPriceAggregator) {
    @GetMapping
    fun getSum(): Long {
        return priceAggregator.getTickSum()
    }
}