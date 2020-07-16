package com.tpjad.shop.checkout;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/checkouts")
@Slf4j
@RequiredArgsConstructor
public class CheckoutAPI {
    private final CheckoutService checkoutService;

    @GetMapping
    public ResponseEntity<List<Checkout>> findByAccountId(@RequestParam("accountId") Long accountId) {
        Optional<List<Checkout>> stock = checkoutService.findByAccountId(accountId);
        if (!stock.isPresent()) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(stock.get());
    }
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Checkout checkout) {
        return ResponseEntity.ok(checkoutService.save(checkout));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Checkout> findById(@PathVariable Long id) {
        Optional<Checkout> stock = checkoutService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(stock.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Checkout> update(@PathVariable Long id, @Valid @RequestBody Checkout checkout) {
        if (!checkoutService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(checkoutService.save(checkout));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (!checkoutService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }

        checkoutService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}


