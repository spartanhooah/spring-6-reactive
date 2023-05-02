package net.frey.spring6reactive.controller

import net.frey.spring6reactive.model.BeerDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

import static net.frey.spring6reactive.controller.BeerController.BEER_PATH

@SpringBootTest
@AutoConfigureWebTestClient
class BeerControllerTest extends Specification {
    @Autowired
    WebTestClient client

    def "list beers"() {
        expect:
        client.get().uri(BEER_PATH)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().valueEquals("Content-Type", "application/json")
            .expectBody().jsonPath('$.size()').isEqualTo(3)
    }

    def "get by id"() {
        expect:
        client.get().uri("$BEER_PATH/1")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().valueEquals("Content-Type", "application/json")
            .expectBody(BeerDTO);
    }
}
