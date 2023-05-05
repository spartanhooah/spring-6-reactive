package net.frey.spring6reactive.controller;

import lombok.RequiredArgsConstructor;
import net.frey.spring6reactive.model.BeerDTO;
import net.frey.spring6reactive.service.BeerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(BeerController.BEER_PATH)
@RequiredArgsConstructor
public class BeerController {
    public static final String BEER_PATH = "/api/v2/beer";

    private final BeerService beerService;

    @GetMapping
    Flux<BeerDTO> listBeers() {
        return beerService.listBeers();
    }

    @GetMapping("/{id}")
    Mono<BeerDTO> getById(@PathVariable int id) {
        return beerService.getById(id).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PostMapping
    Mono<ResponseEntity<Void>> createBeer(@RequestBody @Validated BeerDTO newBeer) {
        return beerService.saveBeer(newBeer).map(savedDto -> ResponseEntity.created(
                        UriComponentsBuilder.fromHttpUrl("http://localhost:8080/" + BEER_PATH + "/" + savedDto.getId())
                                .build()
                                .toUri())
                .build());
    }

    @PutMapping("/{id}")
    Mono<ResponseEntity<Void>> updateBeer(@PathVariable int id, @RequestBody @Validated BeerDTO beer) {
        return beerService
                .updateBeer(id, beer)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(savedDto -> ResponseEntity.noContent().build());
    }

    @PatchMapping("/{id}")
    Mono<ResponseEntity<Void>> patchBeer(@PathVariable int id, @RequestBody @Validated BeerDTO beer) {
        return beerService.patchBeer(id, beer)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
            .map(savedDto -> ResponseEntity.ok().build());
    }
}
