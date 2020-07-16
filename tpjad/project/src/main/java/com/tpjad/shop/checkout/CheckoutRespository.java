package com.tpjad.shop.checkout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface CheckoutRespository extends JpaRepository<Checkout, Long> {
  @Query("SELECT item FROM Checkout item WHERE item.accountId = ?1")
  Optional<List<Checkout>> findByAccountId(Long accountId);
}
