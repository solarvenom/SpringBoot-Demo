package demo.ecommerce.product.dtos;

import jakarta.validation.constraints.NotBlank;

public class ProductDto {
    @NotBlank(message = "Product name is required.")
    private String name;

    @NotBlank(message = "Product description is required.")
    private String description;

    public ProductDto() {}

    public ProductDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}