package demo.ecommerce.order.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.ecommerce.order.entities.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    boolean existsByUuid(UUID uuid);
    
    void deleteByUuid(UUID uuid);
}
