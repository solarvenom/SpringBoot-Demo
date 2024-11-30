package demo.ecommerce.order.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public class DeleteOrderDto {
     @NotBlank(message = "Order uuid is required.")
    private UUID orderUuid;

    protected DeleteOrderDto() {}

    public DeleteOrderDto(
        UUID orderUuid
    ) {
        this.orderUuid = orderUuid;
    }

    public UUID getOrderUuid(){
        return orderUuid;
    }
}
