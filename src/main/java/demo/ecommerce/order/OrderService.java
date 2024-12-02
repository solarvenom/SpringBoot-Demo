package demo.ecommerce.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

import demo.ecommerce.order.dtos.CreateOrderDto;
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

    @Transactional
    public OrderEntity createOrder(CreateOrderDto orderDto){
        ProductVariantEntity productVariant = this.productVariantRepository.findByUuid(orderDto.getProductVariantUuid());
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

    public void deleteOrder(UUID uuid){
        if(!this.orderRepository.existsByUuid(uuid)){
            throw new IllegalArgumentException("Order with UUID " + uuid + " does not exist.");
        }
        this.orderRepository.deleteByUuid(uuid);
    }
}
