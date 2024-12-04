package demo.ecommerce.order.dtos;

import jakarta.validation.constraints.NotBlank;

public class UpdateOrderDto {

    @NotBlank(message = "ProductVariant mapping is required.")
    private String mapping;
    
    protected UpdateOrderDto(){}

    public UpdateOrderDto(
        String mapping
    ){
        this.mapping = mapping;
    }

    public String getMapping(){
        return mapping;
    }
}
