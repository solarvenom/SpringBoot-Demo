package demo.ecommerce.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import demo.ecommerce.api.errors.ApiError;
import demo.ecommerce.order.dtos.CreateOrderDto;
import demo.ecommerce.order.entities.OrderEntity;

public class OrderController {

    @Autowired
    private OrderService orderService;

    OrderController(OrderService orderService){
        this.orderService = orderService;
    }
  
    @GetMapping("/orders")
    public List<OrderEntity> getProducts() {
        return this.orderService.getAllOrders();
    }

    @PostMapping("/orders")
    public ResponseEntity<?> createProduct(@RequestBody CreateOrderDto orderDto) {
        try {
            OrderEntity createdProduct = this.orderService.createOrder(orderDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ApiError(HttpStatus.CONFLICT.value(), "Conflict", e.getMessage(), "@Post /orders")
            );
        }
    }
}
