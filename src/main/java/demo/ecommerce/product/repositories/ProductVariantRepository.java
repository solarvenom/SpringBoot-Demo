package demo.ecommerce.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.ecommerce.product.entities.ProductVariantEntity;

public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity, Long> {
    
}
