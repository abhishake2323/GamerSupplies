package com.starmodestudios.gamersupplies.repository;

import com.starmodestudios.gamersupplies.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
