package com.example.basicspringdatajparepository_sd18202;

import com.example.basicspringdatajparepository_sd18202.methods.entity.Product;
import com.example.basicspringdatajparepository_sd18202.methods.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

        //5. Pagination
        int pageNo = 0; //
        int pageSize = 5;

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        // get all info via page
        Page<Product> page = repository.findAll(pageable);

        List<Product> products = page.getContent();
        products.forEach(p -> logger.info(p.toString()));

        // total pages
        int totalPages = page.getTotalPages();
        logger.info("Total pages: " + totalPages);

        // total elements
        long totalElements = page.getTotalElements();
        logger.info("Total products: " + totalElements);

        // size
        int size = page.getSize();

        // last
        boolean isLast = page.isLast();
        // first
        boolean isFirst = page.isFirst();

        // 6. Sorting
//        List<Product> products1 =
//                repository.findAll(Sort.by("name").descending());
//        products1.forEach(p -> logger.info(p.toString()));


        String sortBy = "name";
        String sortDir = "desc";

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        List<Product> products1 = repository.findAll(sort);
        products1.forEach(p -> logger.info(p.toString()));

        // 7. Pagination + sorting
        Page<Product> products2 =
                repository.findAll(PageRequest.of(pageNo, pageSize, sort));

        products2.forEach(p -> logger.info(p.getName()));
    }
}
