package com.tpjad.shop.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

@RequiredArgsConstructor
public class AccountService {
    private final AccountRespository AccountRespository;

    public List<Account> findAll() {
        return AccountRespository.findAll();
    }

    public Optional<Account> findById(Long id) {
        return AccountRespository.findById(id);
    }

    public Account save(Account stock) {
        return AccountRespository.save(stock);
    }

    public void deleteById(Long id) {
        AccountRespository.deleteById(id);
    }
}
