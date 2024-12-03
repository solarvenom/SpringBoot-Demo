package demo.ecommerce.order;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import demo.ecommerce.api.errors.ApiError;
import demo.ecommerce.order.dtos.CreateOrderDto;
import demo.ecommerce.order.entities.OrderEntity;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    OrderController(OrderService orderService){
        this.orderService = orderService;
    }
  
    @CrossOrigin(origins = "*")
    @GetMapping("/orders")
    public List<OrderEntity> getOrders() {
        return this.orderService.getAllOrders();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderDto orderDto) {
        try {
            OrderEntity createdOrder = this.orderService.createOrder(orderDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ApiError(HttpStatus.CONFLICT.value(), "Conflict", e.getMessage(), "@Post /orders")
            );
        }
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/orders/{uuid}")
    public ResponseEntity<?> deleteOrder(@PathVariable UUID uuid) {
        try {
            this.orderService.softDeleteOrder(uuid);
            return ResponseEntity.status(HttpStatus.OK).body("Order with UUID " + uuid + " has been deleted.");
        } catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ApiError(HttpStatus.CONFLICT.value(), "Conflict", e.getMessage(), "@Delete /orders")
            );
        }
    }
}
