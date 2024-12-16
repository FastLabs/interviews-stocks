package tech.amelio.interview.stocks.logic

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.concurrent.locks.ReentrantReadWriteLock

@Component
/* For performance consideration this Price Listener is designed as single threaded consumer*/
class StockPriceAggregator(private val stockPriceRepository: StockPriceRepository ) : EventListener<StockTick> {
    private var currentTick = -1L
    private var currentSum = 0L
    private val lock = ReentrantReadWriteLock()
    private val readLock = lock.readLock()
    private val writeLock = lock.writeLock()
    companion object {
        private val logger = LoggerFactory.getLogger(StockPriceAggregator::class.java)
    }

    fun getTickSum(): Long {
        try {
            readLock.lock()
            return if (currentTick == -1L) {
                stockPriceRepository.getAllStocks().sumOf { it.price }
            } else {
                currentSum
            }
        } finally {
            readLock.unlock()
        }
    }

    override fun onEvent(event: StockTick) {
        if (currentTick < event.tick) {
            writeLock.lock()
            if (currentTick < event.tick) {
                currentTick = event.tick
                currentSum = event.stockPrice.price
            }
            writeLock.unlock()
        } else if (currentTick == event.tick) {
            currentSum += event.stockPrice.price
        } else {
            logger.warn("Received an expired tick ")
        }
    }

}