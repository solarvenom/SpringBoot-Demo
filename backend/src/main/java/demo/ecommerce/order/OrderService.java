package demo.ecommerce.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

import demo.ecommerce.order.dtos.CreateOrderDto;
import demo.ecommerce.order.dtos.UpdateOrderDto;
import demo.ecommerce.order.entities.OrderEntity;
import demo.ecommerce.order.repositories.OrderRepository;
import demo.ecommerce.product.entities.ProductVariantEntity;
import demo.ecommerce.product.repositories.ProductVariantRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    public List<OrderEntity> getAllOrders(){
        return orderRepository.findAll();
    }

    public List<OrderEntity> searchOrders(String searchTerm){
        if(searchTerm != null){
            return this.orderRepository.findBySearchTerm(searchTerm);
        }
        return this.orderRepository.findAll();
    }

    @Transactional
    public OrderEntity createOrder(CreateOrderDto orderDto){
        UUID productVariantUuid = orderDto.getProductVariantUuid();
        if(!this.productVariantRepository.existsByUuid(productVariantUuid)){
            throw new IllegalArgumentException("Product variant " + productVariantUuid + " does not exist.");
        }
        ProductVariantEntity productVariant = this.productVariantRepository.findByUuid(productVariantUuid);
        Integer productVariantStock = productVariant.getStock();
        if(productVariantStock == 0){
            throw new IllegalArgumentException("Product variant " + productVariant.getSku() + " is out of stock.");
        }
        productVariant.setStock(productVariantStock--);
        this.productVariantRepository.save(productVariant);
        OrderEntity order = new OrderEntity();
        order.setProductVariant(productVariant);
        return this.orderRepository.save(order);
    }

    @Transactional
    public void softDeleteOrder(UUID uuid){
        if(!this.orderRepository.existsByUuid(uuid)){
            throw new IllegalArgumentException("Order with UUID " + uuid + " does not exist.");
        }
        this.orderRepository.softDeletedByUuid(uuid);
    }

    @Transactional
    public void softDeleteProductVariantOrders(UUID uuid){
        this.orderRepository.softDeleteByProductVariantUuid(uuid);
    }

    public OrderEntity updateOrderMapping(UUID uuid, UpdateOrderDto updateOrderDto){
        if(!this.orderRepository.existsByUuid(uuid)){
            throw new IllegalArgumentException("Order with UUID " + uuid + " does not exist.");
        }

        OrderEntity order = this.orderRepository.findByUuid(uuid);
        order.setMapping(updateOrderDto.getMapping());
        return this.orderRepository.save(order);
    }
    
}
