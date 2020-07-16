package com.tpjad.shop.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ProductRespository extends JpaRepository<Product, Long> {
  @Query("SELECT item FROM Product item WHERE item.category = ?1")
  List<Product> findByCategory(String category);
}
