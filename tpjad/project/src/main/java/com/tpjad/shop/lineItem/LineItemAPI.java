package com.tpjad.shop.lineItem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/lineItems")
@Slf4j
@RequiredArgsConstructor
public class LineItemAPI {
    private final LineItemService lineItemService;

    // @GetMapping
    // public ResponseEntity<List<LineItem>> findAll() {
    // return ResponseEntity.ok(lineItemService.findAll());
    // }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody LineItem lineItem) {
        return ResponseEntity.ok(lineItemService.save(lineItem));
    }

    @GetMapping
    public ResponseEntity<List<LineItem>> findByAccountId(@RequestParam("accountId") Long accountId) {
        Optional<List<LineItem>> stock = lineItemService.findByAccountId(accountId);
        if (!stock.isPresent()) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(stock.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LineItem> update(@PathVariable Long id, @Valid @RequestBody LineItem lineItem) {
        if (!lineItemService.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(lineItemService.save(lineItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (!lineItemService.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }

        lineItemService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
