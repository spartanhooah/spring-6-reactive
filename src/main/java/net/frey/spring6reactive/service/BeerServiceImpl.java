package net.frey.spring6reactive.service;

import lombok.RequiredArgsConstructor;
import net.frey.spring6reactive.mapper.BeerMapper;
import net.frey.spring6reactive.model.BeerDTO;
import net.frey.spring6reactive.repository.BeerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {
    private final BeerRepository repository;
    private final BeerMapper mapper;

    @Override
    public Flux<BeerDTO> listBeers() {
        return repository.findAll().map(mapper::entityToDto);
    }

    @Override
    public Mono<BeerDTO> getById(int id) {
        return repository.findById(id).map(mapper::entityToDto);
    }

    @Override
    public Mono<BeerDTO> saveBeer(BeerDTO newBeer) {
        return repository.save(mapper.dtoToEntity(newBeer)).map(mapper::entityToDto);
    }

    @Override
    public Mono<BeerDTO> updateBeer(int id, BeerDTO beer) {
        return repository
                .findById(id)
                .map(foundBeer -> {
                    foundBeer.setBeerName(beer.getBeerName());
                    foundBeer.setBeerStyle(beer.getBeerStyle());
                    foundBeer.setPrice(beer.getPrice());
                    foundBeer.setUpc(beer.getUpc());
                    foundBeer.setQuantityOnHand(beer.getQuantityOnHand());

                    return foundBeer;
                })
                .flatMap(repository::save)
                .map(mapper::entityToDto);
    }

    @Override
    public Mono<BeerDTO> patchBeer(int id, BeerDTO beer) {
        return repository
                .findById(id)
                .map(foundBeer -> {
                    if (beer.getBeerName() != null) {
                        foundBeer.setBeerName(beer.getBeerName());
                    }

                    if (beer.getBeerStyle() != null) {
                        foundBeer.setBeerStyle(beer.getBeerStyle());
                    }

                    if (beer.getUpc() != null) {
                        foundBeer.setUpc(beer.getUpc());
                    }

                    if (beer.getPrice() != null) {
                        foundBeer.setPrice(beer.getPrice());
                    }

                    return foundBeer;
                })
                .flatMap(repository::save)
                .map(mapper::entityToDto);
    }
}
