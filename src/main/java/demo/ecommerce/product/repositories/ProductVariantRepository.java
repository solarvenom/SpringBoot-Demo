package demo.ecommerce.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import demo.ecommerce.product.entities.ProductVariantEntity;

public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity, Long> {
    
    @Query("SELECT pv FROM ProductVariantEntity pv " +
           "JOIN pv.product p " +
           "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(pv.colour) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(pv.size) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR CAST(pv.price AS string) LIKE CONCAT('%', :searchTerm, '%')")
    List<ProductVariantEntity> findBySearchTerm(@Param("searchTerm") String searchTerm);
}
