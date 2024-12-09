package tech.amelio.interview.stocks.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/popular-stocks")
class PopularStocksController {
    @GetMapping
    fun getPopularStocks(): List<String> {
        return listOf("stock-mock-1", "stock-mock-2", "stock-mock-3")
    }
}