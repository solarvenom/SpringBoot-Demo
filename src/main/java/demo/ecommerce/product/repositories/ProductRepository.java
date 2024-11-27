package demo.ecommerce.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.ecommerce.product.entities.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}
