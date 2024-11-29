package demo.ecommerce.order;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.ecommerce.order.entities.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    
}
