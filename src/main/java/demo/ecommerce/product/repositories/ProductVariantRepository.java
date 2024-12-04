package demo.ecommerce.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

import demo.ecommerce.product.entities.ProductVariantEntity;
import demo.ecommerce.product.enums.ColourEnum;
import demo.ecommerce.product.enums.SizeEnum;
import jakarta.transaction.Transactional;

public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity, Long> {
    
    @Query(
        "SELECT pv FROM ProductVariantEntity pv " +
        "JOIN pv.product p " +
        "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
        "OR LOWER(pv.colour) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
        "OR LOWER(pv.size) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
        "OR LOWER(pv.sku) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
        "OR LOWER(pv.ean) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
        "OR CAST(pv.stock AS string) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
        "OR CAST(pv.price AS string) LIKE CONCAT('%', :searchTerm, '%')"
    ) List<ProductVariantEntity> findBySearchTerm(@Param("searchTerm") String searchTerm);

    @Query(
        "SELECT CASE WHEN COUNT(pv) > 0 THEN true ELSE false END " +
        "FROM ProductVariantEntity pv " + 
        "JOIN pv.product p " +
        "WHERE p.uuid = :uuid AND pv.size = :size AND pv.colour = :colour AND pv.price = :price"
    ) boolean existsBySizeAndColourAndPrice(
        @Param("uuid") UUID uuid,
        @Param("size") SizeEnum size,
        @Param("colour") ColourEnum colour,
        @Param("price") Double price
    );

    ProductVariantEntity findByUuid(UUID uuid);

    @Query(
        "SELECT pv FROM ProductVariantEntity pv " +
        "JOIN pv.product p " +
        "WHERE p.uuid = :uuid"
    ) List<ProductVariantEntity> findByProductUuid(@Param("uuid") UUID uuid);

    boolean existsByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

    @Modifying
    @Transactional
    @Query(value = "UPDATE product_variants SET deleted_date = NOW() WHERE uuid = :uuid", nativeQuery = true)
    void softDeletedByUuid(UUID uuid);
}
