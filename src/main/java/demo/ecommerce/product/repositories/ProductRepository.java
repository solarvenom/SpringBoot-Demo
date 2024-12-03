package demo.ecommerce.product.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import demo.ecommerce.product.entities.ProductEntity;
import jakarta.transaction.Transactional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    boolean existsByName(String name);

    boolean existsByUuid(UUID uuid);

    ProductEntity findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

    @Modifying
    @Transactional
    @Query(value = "UPDATE products SET deleted_date = NOW() WHERE uuid = :uuid", nativeQuery = true)
    void softDeletedByUuid(UUID uuid);

    @Query(
        "SELECT p FROM ProductEntity p " +
        "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
        "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))"
    ) 
    List<ProductEntity> findBySearchTerm(String searchTerm);
}
