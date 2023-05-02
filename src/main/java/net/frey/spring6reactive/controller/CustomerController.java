package net.frey.spring6reactive.controller;

import static org.springframework.http.ResponseEntity.ok;

import lombok.RequiredArgsConstructor;
import net.frey.spring6reactive.model.CustomerDTO;
import net.frey.spring6reactive.service.CustomerService;
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
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(CustomerController.CUSTOMER_PATH)
@RequiredArgsConstructor
public class CustomerController {
    public static final String CUSTOMER_PATH = "/api/v2/customer";

    private final CustomerService customerService;

    @GetMapping
    Flux<CustomerDTO> listCustomers() {
        return customerService.listCustomers();
    }

    @GetMapping("/{id}")
    Mono<CustomerDTO> getById(@PathVariable int id) {
        return customerService.getById(id);
    }

    @PostMapping
    Mono<ResponseEntity<Void>> createCustomer(@RequestBody @Validated CustomerDTO newCustomer) {
        return customerService.saveCustomer(newCustomer).map(savedDto -> ResponseEntity.created(
                        UriComponentsBuilder.fromHttpUrl(
                                        "http://localhost:8080/" + CUSTOMER_PATH + "/" + savedDto.getId())
                                .build()
                                .toUri())
                .build());
    }

    @PutMapping("/{id}")
    Mono<ResponseEntity<Void>> updateCustomer(@PathVariable int id, @RequestBody @Validated CustomerDTO customer) {
        return customerService.updateCustomer(id, customer).map(savedDto -> ok().build());
    }

    @PatchMapping("/{id}")
    Mono<ResponseEntity<Void>> patchCustomer(@PathVariable int id, @RequestBody @Validated CustomerDTO customer) {
        return customerService.patchCustomer(id, customer).map(savedDto -> ok().build());
    }
}
