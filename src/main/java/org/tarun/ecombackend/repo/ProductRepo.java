package org.tarun.ecombackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tarun.ecombackend.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
}
