package tech.amelio.interview.stocks.logic

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

@Component
class StockAccessMonitor {

    private val perStockAccessCount: ConcurrentMap<String, Long> = ConcurrentHashMap()

    fun getTopPopularStocks(top: Int): List<String> {
        return perStockAccessCount.entries.sortedByDescending { it.value }.map { it.key }.take(top)
    }

    fun recordStockAccessed(stockName: String) {
        perStockAccessCount.compute(stockName) { _, v -> if (v == null) 0 else v + 1 }
    }
}