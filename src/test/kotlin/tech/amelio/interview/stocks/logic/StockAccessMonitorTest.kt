package tech.amelio.interview.stocks.logic

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class StockAccessMonitorTest {

    @Test
    fun `recording first stock access stats` () {
        val stockAccessMonitor = StockAccessMonitor()
        stockAccessMonitor.recordStockAccessed("stock-1")
        stockAccessMonitor.recordStockAccessed("stock-2")
        assertEquals( setOf("stock-1", "stock-2"), stockAccessMonitor.getTopPopularStocks(3).toSet())
        stockAccessMonitor.recordStockAccessed("stock-2")
        assertEquals(listOf("stock-2", "stock-1"), stockAccessMonitor.getTopPopularStocks(3))
    //change the situation
        stockAccessMonitor.recordStockAccessed("stock-1")
        stockAccessMonitor.recordStockAccessed("stock-1")
        assertEquals(listOf("stock-1", "stock-2"), stockAccessMonitor.getTopPopularStocks(3))
    }
}