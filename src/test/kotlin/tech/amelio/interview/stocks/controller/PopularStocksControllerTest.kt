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

class PopularStocksControllerTest {
    @Autowired
    lateinit var priceTicker: PriceTicker

    @LocalServerPort
    private val port = 0

    @Test
    fun `returns the stock value`() {
        priceTicker.nextTick()
        val client = WebClient.builder().build()
        val data = client.get()
            .uri("http://localhost:$port/stocks/stock-1")
            .retrieve().bodyToMono<Long>()
            .block()
        repeat(2) {
            client.get()
                .uri("http://localhost:$port/stocks/stock-2")
                .retrieve().bodyToMono<Long>()
                .block()
        }
        val topStocks = client.get()
            .uri("http://localhost:$port/popular-stocks")
            .retrieve().bodyToMono<List<String>>()
            .block()!!
        assertEquals(listOf("stock-2", "stock-1"), topStocks)
    }

}