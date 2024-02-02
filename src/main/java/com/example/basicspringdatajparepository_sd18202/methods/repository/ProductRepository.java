package com.example.basicspringdatajparepository_sd18202.methods.repository;

import com.example.basicspringdatajparepository_sd18202.methods.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 2. containing
    List<Product> findByNameContaining(String name);

    // 3. JPQL with index
    @Query("SELECT p FROM Product p WHERE p.name=?1 OR p.id=?2")
    List<Product> findByNameOrIdJpqlIndexParam(String name, long id);

    @Query(value = "SELECT * FROM Product p WHERE p.name=:name OR p.id=:id", nativeQuery = true)
    List<Product> findByNameOrIdSqlNamedParam(String name, long id);

    //4. Named queries
    List<Product> findByNameVersion4(String name);

    @Query(nativeQuery = true)
    List<Product> findByNameVersion4b(String name);

}
