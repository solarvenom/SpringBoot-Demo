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

import java.util.Random;
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
    @Column(nullable = false)
    private ColourEnum colour;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SizeEnum size;

    @Column(nullable = false)
    private Float price;

    @Column(unique = true, nullable = false)
    private String sku;

    @Column(unique = true, nullable = false)
    private String ean;

    @PrePersist
    public void generateUuid() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }

    @PrePersist
    public void generateSku() {
        if (sku == null) {
            UUID productUuid = product.getUuid();
            String[] identifier = productUuid.toString().split("-");
            sku = identifier[4] + "-" + colour.toString() + "-" + size.toString();
        }
    }

    @PrePersist
    public void generateEan(){
        if(ean == null){
            Random random = new Random();
            long number = 1000000000L + (long) (random.nextDouble() * 9000000000L);
            ean = "73" + String.valueOf(number) + "K";
        }
    }

    protected ProductVariantEntity(){}

    public ProductVariantEntity(
        ColourEnum colour,
        SizeEnum size,
        Float price
        // String sku,
        // String ean
    ) {
        this.colour = colour;
        this.size = size;
        this.price = price;
        // this.sku = sku;
        // this.ean = ean;
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

    public void setProduct(ProductEntity product){
        this.product = product;
    }
}
