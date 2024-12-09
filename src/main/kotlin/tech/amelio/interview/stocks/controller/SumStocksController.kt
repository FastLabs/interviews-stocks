package tech.amelio.interview.stocks.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sum-stocks")
class SumStocksController {
    @GetMapping
    fun getSum(): Long {
        return 0
    }
}