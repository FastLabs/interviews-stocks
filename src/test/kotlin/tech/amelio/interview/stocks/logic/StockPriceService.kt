package tech.amelio.interview.stocks.logic

import org.junit.jupiter.api.assertThrows
import java.lang.RuntimeException
import java.util.concurrent.ExecutionException
import kotlin.test.Test
import kotlin.test.assertEquals

class StockPriceService {
    @Test
    fun testPriceComputedAsync() {
        val priceCalculationService = PriceCalculationService { _, currentPrice -> currentPrice + 1 }
        val nextPrice = priceCalculationService.getNewStockPrice(StockPrice("stock-1", 10))
        assertEquals(nextPrice.get(), StockPrice("stock-1", 11))
    }

    @Test
    fun testExceptionThrownDuringCalculation() {
        val priceCalculationService = PriceCalculationService {_, _-> throw RuntimeException("Price failed") }
        val nextPrice = priceCalculationService.getNewStockPrice(StockPrice("stock-1", 10))
        val exception = assertThrows<ExecutionException> {  nextPrice.get()}
        assertEquals(exception.cause?.message, "Price failed")
    }
}