package tech.amelio.interview.stocks.logic

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

data class StockPrice(val stockName: String, val price: Long)
data class StockTick(val tick: Long, val stockPrice: StockPrice)
@Component
class PriceTicker(
    private val priceCalculationService: PriceCalculationService,
    private val stockPriceRepository: StockPriceRepository,
    private val stockPriceQueue: SimpleQueue<StockTick>,
) {
    companion object {
        private val logger = LoggerFactory.getLogger(PriceTicker::class.java)
    }
    private val scheduledExecutor = Executors.newSingleThreadScheduledExecutor()

    fun startTicker() {
        scheduledExecutor.scheduleAtFixedRate(this::nextTick, 0, 1, TimeUnit.SECONDS)

    }

     fun nextTick() {
        val currentTick = Instant.now().toEpochMilli()
        logger.info("Tick: $currentTick")
        val allStockPrices = stockPriceRepository.getAllStocks()
        val countDownLatch = CountDownLatch(allStockPrices.size)
        val allStockPricesFutures = allStockPrices
            .map { priceCalculationService.getNewStockPrice(it) }
        allStockPricesFutures.forEach { f ->
            f.handle { res, err ->
                if (err == null) {
                    stockPriceQueue.addToQueue(StockTick(currentTick, res))
                } else {
                    logger.error("Error when calculating the price for xxx", err.cause)
                }
                countDownLatch.countDown()
            }
        }
        countDownLatch.await()
    }


}