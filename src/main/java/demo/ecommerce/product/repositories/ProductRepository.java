package demo.ecommerce.product.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.ecommerce.product.entities.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    boolean existsByName(String name);

    ProductEntity findByUuid(UUID uuid);
}
