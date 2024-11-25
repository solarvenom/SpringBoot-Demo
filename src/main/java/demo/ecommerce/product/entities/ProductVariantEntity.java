package demo.ecommerce.product.entities;

import demo.ecommerce.product.enums.ColourEnum;
import demo.ecommerce.product.enums.SizeEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name="product_variants")
public class ProductVariantEntity {

    @ManyToOne
    @JoinColumn(name="product_id")
    private ProductEntity product;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private ColourEnum colour;

    private SizeEnum size;

    private Float price;

    private String sku;

    private String ean;

    public ProductVariantEntity(
        ColourEnum colour,
        SizeEnum size,
        Float price,
        String sku,
        String ean
    ) {
        this.colour = colour;
        this.size = size;
        this.price = price;
        this.sku = sku;
        this.ean = ean;
    }
}
