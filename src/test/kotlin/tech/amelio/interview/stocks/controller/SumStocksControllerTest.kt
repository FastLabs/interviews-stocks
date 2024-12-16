package tech.amelio.interview.stocks.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import tech.amelio.interview.stocks.logic.PriceTicker
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")

class SumStocksControllerTest {
    @Autowired
    lateinit var priceTicker: PriceTicker

    @LocalServerPort
    private val port = 0


    @Test
    fun `checks the sum value per tick`() {
        priceTicker.nextTick()
        val client = WebClient.builder().build()
        val stockPricesAdded = (1..10).map {
            client.get()
                .uri("http://localhost:$port/stocks/stock-$it")
                .retrieve().bodyToMono<Long>()
                .block()
        }.sumOf { it!! }


        val sum = client.get()
            .uri("http://localhost:$port/sum-stocks")
            .retrieve().bodyToMono<Long>()
            .block()
        assertEquals(stockPricesAdded, sum)
    }
}