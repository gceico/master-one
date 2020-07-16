package com.tpjad.shop.checkout;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

@RequiredArgsConstructor
public class CheckoutService {
    private final CheckoutRespository checkoutRespository;

    public List<Checkout> findAll() {
        return checkoutRespository.findAll();
    }

    public Optional<Checkout> findById(Long id) {
        return checkoutRespository.findById(id);
    }

    public Optional<List<Checkout>>  findByAccountId(Long accountId) {
        return checkoutRespository.findByAccountId(accountId);
    }

    public Checkout save(Checkout stock) {
        return checkoutRespository.save(stock);
    }

    public void deleteById(Long id) {
        checkoutRespository.deleteById(id);
    }
}
