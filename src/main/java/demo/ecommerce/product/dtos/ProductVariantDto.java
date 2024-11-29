package demo.ecommerce.product.dtos;

import demo.ecommerce.product.enums.ColourEnum;
import demo.ecommerce.product.enums.SizeEnum;

public class ProductVariantDto {
    private ColourEnum colour;

    private SizeEnum size;

    private Float price;

    private String sku;

    private String ean;

    private Integer stock;

    public ProductVariantDto() {}

    public ProductVariantDto(
        ColourEnum colour, 
        SizeEnum size,
        Float price,
        String sku,
        String ean,
        Integer stock
    ) {
        this.colour = colour;
        this.size = size;
        this.price = price;
        this.sku = sku;
        this.ean = ean;
        this.stock = stock;
    }

    public ColourEnum getColour() {
        return colour;
    }

    public SizeEnum getSize() {
        return size;
    }

    public Float getPrice() {
        return price;
    }

    public String getSku(){
        return sku;
    }

    public String getEan(){
        return ean;
    }

    public Integer getStock(){
        return stock;
    }
}
