package net.frey.spring6reactive.bootstrap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.frey.spring6reactive.domain.Beer;
import net.frey.spring6reactive.domain.Customer;
import net.frey.spring6reactive.repository.BeerRepository;
import net.frey.spring6reactive.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) {
        LocalDateTime now = LocalDateTime.now();
        loadBeerData(now);
        loadCustomerData(now);

        beerRepository.count().subscribe(count -> System.out.println("Beer count is: " + count));
        customerRepository.count().subscribe(count -> System.out.println("Customer count is: " + count));
    }

    private void loadBeerData(LocalDateTime now) {
        beerRepository.count().subscribe(count -> {
            if (count == 0) {
                Beer beer1 = Beer.builder()
                        .beerName("Galaxy Cat")
                        .beerStyle("Pale Ale")
                        .upc("12356")
                        .price(new BigDecimal("12.99"))
                        .quantityOnHand(122)
                        .createdDate(now)
                        .lastModifiedDate(now)
                        .build();

                Beer beer2 = Beer.builder()
                        .beerName("Crank")
                        .beerStyle("Pale Ale")
                        .upc("12356222")
                        .price(new BigDecimal("11.99"))
                        .quantityOnHand(392)
                        .createdDate(now)
                        .lastModifiedDate(now)
                        .build();

                Beer beer3 = Beer.builder()
                        .beerName("Sunshine City")
                        .beerStyle("IPA")
                        .upc("12356")
                        .price(new BigDecimal("13.99"))
                        .quantityOnHand(144)
                        .createdDate(now)
                        .lastModifiedDate(now)
                        .build();

                beerRepository.save(beer1).subscribe();
                beerRepository.save(beer2).subscribe();
                beerRepository.save(beer3).subscribe();
            }
        });
    }

    private void loadCustomerData(LocalDateTime now) {
        customerRepository.count().subscribe(count -> {
            if (count == 0) {
                Customer customer1 = Customer.builder()
                        .name("Michael Weston")
                        .createdDate(now)
                        .lastModifiedDate(now)
                        .build();

                Customer customer2 = Customer.builder()
                        .name("Fiona Glennane")
                        .createdDate(now)
                        .lastModifiedDate(now)
                        .build();

                Customer customer3 = Customer.builder()
                        .name("Sam Axe")
                        .createdDate(now)
                        .lastModifiedDate(now)
                        .build();

                customerRepository
                        .saveAll(List.of(customer1, customer2, customer3))
                        .subscribe();
            }
        });
    }
}
