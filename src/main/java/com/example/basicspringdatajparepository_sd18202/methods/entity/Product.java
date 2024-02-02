package com.example.basicspringdatajparepository_sd18202.methods.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@NamedQuery(
        name = "Product.findByNameVersion4",
        query = "SELECT p FROM Product p WHERE p.name = ?1"
)
@NamedNativeQuery(
        name = "Product.findByNameVersion4b",
        query = "SELECT * FROM Product p WHERE p.name = ?1",
        resultClass = Product.class
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private double price;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
