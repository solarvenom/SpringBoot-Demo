package demo.ecommerce.product.dtos;

import java.util.UUID;

import demo.ecommerce.product.enums.ColourEnum;
import demo.ecommerce.product.enums.SizeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CreateProductVariantDto {
    @NotBlank(message = "ProductVariant colour is required.")
    @Pattern(regexp = "RED|GREEN|BLUE", message = "Colour must be one of: RED, GREEN, BLUE")
    private ColourEnum colour;

    @NotBlank(message = "ProductVariant size is required.")
    @Pattern(regexp = "S|M|L", message = "Size must be one of: S, M, L")
    private SizeEnum size;

    @NotBlank(message = "ProductVariant price is required.")
    private Float price;

    @NotBlank(message = "Product UUID is required")
    private UUID productUuid;

    public CreateProductVariantDto() {}

    public CreateProductVariantDto(
        ColourEnum colour,
        SizeEnum size,
        Float price,
        UUID productUuid
    ) {
        this.colour = colour;
        this.size = size;
        this.price = price;
        this.productUuid = productUuid;
    }

    public ColourEnum getColour() {
        return colour;
    }

    public void setColour(ColourEnum colour) {
        this.colour = colour;
    }

    public SizeEnum getSize() {
        return size;
    }

    public void setSize(SizeEnum size) {
        this.size = size;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public UUID getProductUuid(){
        return productUuid;
    }

    public void setProductUuid(UUID productUuid){
        this.productUuid = productUuid;
    }
}
