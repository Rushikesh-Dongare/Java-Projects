package com.Rushikesh.Ecommerce.Repo;

import com.Rushikesh.Ecommerce.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    @Query("SELECT p from Product p where "+
    "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "+
    "LOWER(p.descrption) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "+
    "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "+
    "LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProducts(String keyword);
}
