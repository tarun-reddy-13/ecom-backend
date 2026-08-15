package org.tarun.ecombackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tarun.ecombackend.model.Product;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    public List<Product> findByNameContainingOrDescriptionContainingOrBrandContainingOrCategoryContaining(String name, String description, String brand, String category);
}
