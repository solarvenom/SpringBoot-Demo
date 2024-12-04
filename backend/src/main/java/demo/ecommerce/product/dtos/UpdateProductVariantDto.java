package demo.ecommerce.product.dtos;

public class UpdateProductVariantDto {
    private Double price;

    private Integer stock;

    public UpdateProductVariantDto() {}

    public UpdateProductVariantDto(Double price, Integer stock) {
        this.price = price;
        this.stock = stock;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }
}
