package tech.amelio.interview.stocks.logic

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class StockPriceAggregatorTest {

    @Test
    fun `when no tick received the aggregator responds the aggregated repository values`() {
        val stockPriceRepository = StockPriceRepository(setOf("stock-1", "stock-2", "stock-3"))
        val tickAggregator = StockPriceAggregator(stockPriceRepository)
        assertEquals(tickAggregator.getTickSum(), 3000L)
    }
    @Test
    fun `fresh tick resets the aggregated value`() {
        val stockPriceRepository = StockPriceRepository(setOf("stock-1", "stock-2", "stock-3"))
        val tickAggregator = StockPriceAggregator(stockPriceRepository)
        //first tick
        tickAggregator.onEvent(StockTick(10, StockPrice("stock-1", 330)))
        assertEquals(tickAggregator.getTickSum(), 330)
        tickAggregator.onEvent(StockTick(10, StockPrice("stock-2", 331)))
        assertEquals(tickAggregator.getTickSum(), 661)

        //next tick
        tickAggregator.onEvent(StockTick(11, StockPrice("stock-1", 550)))
        assertEquals(tickAggregator.getTickSum(), 550)
        tickAggregator.onEvent(StockTick(11, StockPrice("stock-2", 551)))
        assertEquals(tickAggregator.getTickSum(), 1101)
    }

    @Test
    fun `aggregated sum not updated when an out of date tick is received`() {
        val stockPriceRepository = StockPriceRepository(setOf("stock-1", "stock-2", "stock-3"))
        val tickAggregator = StockPriceAggregator(stockPriceRepository)
        //first tick
        tickAggregator.onEvent(StockTick(10, StockPrice("stock-1", 330)))
        //old thick
        tickAggregator.onEvent(StockTick(9, StockPrice("stock-1", 220)))
        assertEquals(tickAggregator.getTickSum(), 330)
    }

}