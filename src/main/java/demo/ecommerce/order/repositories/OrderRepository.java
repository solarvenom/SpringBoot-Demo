package demo.ecommerce.order.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

import demo.ecommerce.order.entities.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    boolean existsByUuid(UUID uuid);
    
    void deleteByUuid(UUID uuid);

    @Modifying
    @Transactional
    @Query(value = "UPDATE orders SET deleted_date = NOW() WHERE uuid = :uuid", nativeQuery = true)
    void softDeletedByUuid(UUID uuid);

    @Transactional
    @Query(
        "SELECT o FROM OrderEntity o " +
        "JOIN o.productVariant pv " +
        "WHERE pv.uuid = :uuid"
    ) void softDeleteByProductVariantUuid(@Param("uuid") UUID uuid);
}
