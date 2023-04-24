package net.frey.spring6reactive.repository

import net.frey.spring6reactive.config.DatabaseConfig
import net.frey.spring6reactive.domain.Beer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.context.annotation.Import
import spock.lang.Specification

@DataR2dbcTest
@Import(DatabaseConfig)
class BeerRepositoryTest extends Specification {
    @Autowired
    BeerRepository repo

    def "save a beer"() {
        expect:
        repo.save(buildTestBeer())
            .subscribe { println it }
    }

    static def buildTestBeer() {
        Beer.builder()
            .beerName("Space Dust")
            .beerStyle("IPA")
            .price(BigDecimal.TEN)
            .quantityOnHand(12)
            .upc("123213")
            .build()
    }
}
