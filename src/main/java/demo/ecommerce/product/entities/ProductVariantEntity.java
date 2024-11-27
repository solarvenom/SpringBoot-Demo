package demo.ecommerce.product.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.util.UUID;

import demo.ecommerce.product.enums.ColourEnum;
import demo.ecommerce.product.enums.SizeEnum;

@Entity
@Table(name="product_variants")
public class ProductVariantEntity {

    @ManyToOne
    @JoinColumn(name="product_id")
    private ProductEntity product;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "UUID")
    private UUID uuid;

    @Enumerated(EnumType.STRING)
    private ColourEnum colour;

    @Enumerated(EnumType.STRING)
    private SizeEnum size;

    private Float price;

    private String sku;

    private String ean;

    @PrePersist
    public void generateUuid() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }

    protected ProductVariantEntity(){}

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

    public UUID getUuid(){
        return uuid;
    }

    public ColourEnum getColour() {
        return colour;
    }

    public SizeEnum getSize() {
        return size;
    }

    public Float getPrice(){
        return price;
    }

    public String getSku(){
        return sku;
    }

    public String getEan(){
        return ean;
    }

    public ProductEntity getProduct(){
        return product;
    }
}
