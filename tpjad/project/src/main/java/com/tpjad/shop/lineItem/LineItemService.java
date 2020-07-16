package com.tpjad.shop.lineItem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

@RequiredArgsConstructor
public class LineItemService {
    private final com.tpjad.shop.lineItem.LineItemRespository lineItemRespository;

    public List<LineItem> findAll() {
        return lineItemRespository.findAll();
    }

    public Optional<LineItem> findById(Long id) {
        return lineItemRespository.findById(id);
    }

    public Optional<List<LineItem>>  findByAccountId(Long accountId) {
        return lineItemRespository.findByAccountId(accountId);
    }

    public LineItem save(LineItem stock) {
        return lineItemRespository.save(stock);
    }

    public void deleteById(Long id) {
        lineItemRespository.deleteById(id);
    }
}
