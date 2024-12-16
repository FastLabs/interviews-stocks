package tech.amelio.interview.stocks.logic

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class StockPriceRepository(stockNames: Set<String>) : EventListener<StockTick> {
    private val stocks = ConcurrentHashMap<String, StockPrice>()

    init {
        stocks.putAll(stockNames.map { Pair(it, StockPrice(it, 1000)) })
    }

    fun getAllStocks(): List<StockPrice> {
        return stocks.values.toList()
    }

    fun getStockPrice(stockName: String): StockPrice {

        return stocks[stockName] ?: throw StockNotFoundException(stockName)
    }

    fun updateStockPrice(stockPrice: StockPrice) {
        stocks[stockPrice.stockName] = stockPrice
    }

    override fun onEvent(event: StockTick) {
        updateStockPrice(event.stockPrice)
    }
}

class StockNotFoundException(stockName: String) : RuntimeException("Stock price not found: $stockName")