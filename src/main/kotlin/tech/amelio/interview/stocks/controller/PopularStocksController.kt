package tech.amelio.interview.stocks.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.amelio.interview.stocks.logic.StockAccessMonitor

@RestController
@RequestMapping("/popular-stocks")
class PopularStocksController(private val stockAccessMonitor: StockAccessMonitor) {
    @GetMapping
    fun getPopularStocks(): List<String> {
        return stockAccessMonitor.getTopPopularStocks(3)
    }
}