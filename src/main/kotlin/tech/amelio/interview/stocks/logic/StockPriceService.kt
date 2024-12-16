package tech.amelio.interview.stocks.logic

import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

@Component
class PriceCalculationService(private val getNextStockPriceFn: (String, Long) -> Long = ::getNextStockPrice) {
    private val threadPool = Executors.newCachedThreadPool()
    fun getNewStockPrice(stockPrice: StockPrice): CompletableFuture<StockPrice> {
        val result = CompletableFuture<StockPrice>()
        threadPool.submit {
            try {
                val newPrice = getNextStockPriceFn(stockPrice.stockName, stockPrice.price)
                result.complete(stockPrice.copy(price = newPrice))
            } catch (e: Throwable) {
                result.completeExceptionally(e)
            }
        }
        return result
    }
}