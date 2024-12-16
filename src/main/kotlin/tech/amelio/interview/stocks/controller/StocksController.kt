package tech.amelio.interview.stocks.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import tech.amelio.interview.stocks.logic.StockAccessMonitor
import tech.amelio.interview.stocks.logic.StockNotFoundException
import tech.amelio.interview.stocks.logic.StockPriceRepository

@RestController
@RequestMapping("/stocks")

class StocksController(private val stockPriceRepository: StockPriceRepository, private val stockAccessMonitor: StockAccessMonitor) {
    @GetMapping("{name}")
    fun getStockValue(@PathVariable name: String): Long {
        try {
            stockAccessMonitor.recordStockAccessed(name) //I found that for now the monitor should be linked to the controller
            return stockPriceRepository.getStockPrice(name).price
        } catch (ex: StockNotFoundException) {
            //TODO: springify the exception
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Foo Not Found", ex)
        }
    }
}