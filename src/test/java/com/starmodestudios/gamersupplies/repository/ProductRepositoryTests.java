package com.starmodestudios.gamersupplies.repository;

import com.starmodestudios.gamersupplies.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSaveAndFindProduct() {
        Product product = new Product();
        product.setName("Controller");
        product.setSku("CTRL-01");
        product.setCategory("Accessories");
        product.setDescription("A good controller");
        product.setPrice(59.99);
        product.setQuantity(20);

        // Save
        Product savedProduct = productRepository.save(product);

        // Find
        Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());

        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("Controller");
        assertThat(foundProduct.get().getSku()).isEqualTo("CTRL-01");
    }

    @Test
    public void testFindAll() {
        Product product1 = new Product();
        product1.setName("Item 1");
        product1.setSku("ITM-01");
        product1.setCategory("Games");
        product1.setDescription("Desc 1");
        product1.setPrice(10.00);
        product1.setQuantity(5);

        Product product2 = new Product();
        product2.setName("Item 2");
        product2.setSku("ITM-02");
        product2.setCategory("Consoles");
        product2.setDescription("Desc 2");
        product2.setPrice(20.00);
        product2.setQuantity(10);

        entityManager.persist(product1);
        entityManager.persist(product2);
        entityManager.flush();

        List<Product> products = productRepository.findAll();

        assertThat(products).hasSize(2);
    }
}
