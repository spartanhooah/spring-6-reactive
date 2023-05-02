package net.frey.spring6reactive.service;

import net.frey.spring6reactive.model.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerService {
    Flux<BeerDTO> listBeers();

    Mono<BeerDTO> getById(int id);

    Mono<BeerDTO> saveBeer(BeerDTO newBeer);

    Mono<BeerDTO> updateBeer(int id, BeerDTO beer);

    Mono<BeerDTO> patchBeer(int id, BeerDTO beer);
}
