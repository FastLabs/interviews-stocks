package tech.amelio.interview.stocks.logic

import org.junit.jupiter.api.Test
import java.util.concurrent.CountDownLatch
import kotlin.test.assertEquals

class PriceTickerTest {

    @Test
    fun testTickerCalculatesThePrice() {
        val stockRepository = StockPriceRepository(setOf("stock-1", "stock-2", "stock-3"))
        val priceCalculationService = PriceCalculationService { _, currentPrice -> currentPrice + 1 }
        val receivedPrices = mutableListOf<StockPrice>()
        val countDownLatch = CountDownLatch(3)
        val listener = object : EventListener<StockTick> {
            override fun onEvent(event: StockTick) {
                receivedPrices.add(event.stockPrice)
                countDownLatch.countDown()

            }
        }
        val simpleQueue = SimpleQueue<StockTick>()
        simpleQueue.addEventListener(listener)
        val priceTicker = PriceTicker(priceCalculationService, stockRepository, simpleQueue)
        priceTicker.startTicker()
        countDownLatch.await()
        assertEquals(
            setOf(StockPrice("stock-1", 1001), StockPrice("stock-2", 1001), StockPrice("stock-3", 1001)),
            receivedPrices.toSet()
        )
    }

    @Test
    fun testTickerHandlesTheCalculationError() {

    }


}