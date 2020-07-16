package com.tpjad.shop.lineItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface LineItemRespository extends JpaRepository<LineItem, Long> {
  @Query("SELECT item FROM LineItem item WHERE item.accountId = ?1")
  Optional<List<LineItem>> findByAccountId(Long accountId);
}
