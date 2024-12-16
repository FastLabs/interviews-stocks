package tech.amelio.interview.stocks.logic

import java.util.concurrent.BlockingQueue
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

interface EventListener<T : Any> {
    fun onEvent(event: T)
}

class SimpleQueue<T : Any> {
    private val tickQueue: BlockingQueue<T> = LinkedBlockingQueue()
    private val executorService = Executors.newSingleThreadExecutor()
    private val eventListeners: MutableList<EventListener<T>> = mutableListOf()

    init {
        executorService.submit {
            try {
                while (true) {
                    val stockTick = tickQueue.take()
                    for (priceListener in eventListeners) {
                        priceListener.onEvent(stockTick)
                    }

                }
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
//TODO: log the exception
            }
        }
    }

    fun addEventListener(vararg listener: EventListener<T>) {
        eventListeners.addAll(listener)
    }

    fun addToQueue(tick: T) {
        tickQueue.add(tick)
    }
}