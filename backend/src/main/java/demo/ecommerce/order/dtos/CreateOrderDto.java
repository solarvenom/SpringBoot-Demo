package demo.ecommerce.order.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public class CreateOrderDto {
    @NotBlank(message = "ProductVariant uuid is required.")
    private UUID productVariantUuid;

    protected CreateOrderDto() {}

    public CreateOrderDto(
        UUID productVariantUuid
    ) {
        this.productVariantUuid = productVariantUuid;
    }

    public UUID getProductVariantUuid(){
        return productVariantUuid;
    }
}
