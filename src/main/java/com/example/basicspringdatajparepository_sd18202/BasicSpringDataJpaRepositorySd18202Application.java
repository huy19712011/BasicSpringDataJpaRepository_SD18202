package com.example.basicspringdatajparepository_sd18202;

import com.example.basicspringdatajparepository_sd18202.methods.entity.Product;
import com.example.basicspringdatajparepository_sd18202.methods.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BasicSpringDataJpaRepositorySd18202Application implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(getClass());

    private final ProductRepository repository;

    public BasicSpringDataJpaRepositorySd18202Application(ProductRepository repository) {
        this.repository = repository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BasicSpringDataJpaRepositorySd18202Application.class, args);

        System.out.println("running...");
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Basic Methods: save, ...
        // create Product
        Product product = new Product();
        product.setName("Product 1");
        product.setPrice(100);
        // save to database
        repository.save(product);
        // display info
        repository.findAll().forEach(p -> logger.info(p.toString()));

        // 2. Query Methods (Finder Methods)
        // containing
        repository
                .findByNameContaining("Product 1")
                .forEach(p -> logger.info(p.getName()));

        // 3. JPQL
        List<Product> foundProducts =
                repository.findByNameOrIdJpqlIndexParam("Product 1", 1002);
        foundProducts.forEach(p -> logger.info(p.toString()));

        // 4. Named queries
        List<Product> foundProducts4 = repository.findByNameVersion4("Product 1");
        foundProducts4.forEach(p -> logger.info(p.toString()));

        List<Product> foundProducts4b = repository.findByNameVersion4b("Product 1002");
        foundProducts4b.forEach(p -> logger.info(p.toString()));


    }
}
