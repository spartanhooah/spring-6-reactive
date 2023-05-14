package net.frey.spring6reactive.controller

import net.frey.spring6reactive.model.BeerDTO
import net.frey.spring6reactive.repository.BeerRepositoryTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Stepwise

import static net.frey.spring6reactive.controller.BeerController.BEER_PATH
import static net.frey.spring6reactive.repository.BeerRepositoryTest.buildTestBeer
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login
import static reactor.core.publisher.Mono.just

@Stepwise
@SpringBootTest
@AutoConfigureWebTestClient
class BeerControllerTest extends Specification {
    @Autowired
    WebTestClient client

    def "list beers"() {
        expect:
        client
            .mutateWith(mockOAuth2Login())
            .get().uri(BEER_PATH)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().valueEquals("Content-Type", "application/json")
            .expectBody().jsonPath('$.size()').isEqualTo(3)
    }

    def "get by id"() {
        expect:
        client
            .mutateWith(mockOAuth2Login())
            .get().uri("$BEER_PATH/1")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().valueEquals("Content-Type", "application/json")
            .expectBody(BeerDTO)
    }

    def "create a new beer"() {
        expect:
        client
            .mutateWith(mockOAuth2Login())
            .post().uri(BEER_PATH)
            .body(just(buildTestBeer()), BeerDTO)
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().location("http://localhost:8080$BEER_PATH/4")
    }

    def "update a beer"() {
        expect:
        client
            .mutateWith(mockOAuth2Login())
            .put().uri("$BEER_PATH/1")
            .body(just(buildTestBeer()), BeerDTO)
            .exchange()
            .expectStatus().isNoContent()
    }

    def "create a new beer but there's an error"() {
        given:
        def beer = buildTestBeer()
        beer.beerName = ""

        expect:
        client
            .mutateWith(mockOAuth2Login())
            .post().uri(BEER_PATH)
            .body(just(beer), BeerDTO)
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().isBadRequest()
    }

    def "update a beer but there's an error"() {
        given:
        def beer = buildTestBeer()
        beer.beerStyle = ""

        expect:
        client
            .mutateWith(mockOAuth2Login())
            .put().uri("$BEER_PATH/1")
            .body(just(beer), BeerDTO)
            .exchange()
            .expectStatus().isBadRequest()
    }

    def "get by id but there's an error"() {
        expect:
        client
            .mutateWith(mockOAuth2Login())
            .get().uri("$BEER_PATH/999")
            .exchange()
            .expectStatus().isNotFound()
    }

    def "update a beer that doesn't exist"() {
        expect:
        client
            .mutateWith(mockOAuth2Login())
            .put().uri("$BEER_PATH/999")
            .body(just(buildTestBeer()), BeerDTO)
            .exchange()
            .expectStatus().isNotFound()
    }
}
