package demo.ecommerce.order.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.UUID;

import demo.ecommerce.product.entities.ProductVariantEntity;

@Entity
@Table(name="orders")
public class OrderEntity {
    
    @ManyToOne
    @JoinColumn(name="product_variant_id")
    private ProductVariantEntity productVariant;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "UUID")
    private UUID uuid;

    @CreationTimestamp
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Instant createdDate;

    @Column(updatable = true, nullable = true)
    private Instant deletedDate;

    @Column(updatable = true, nullable = true)
    private String mapping;

    @PrePersist
    public void generateUuid() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }

        if(mapping == null) {
            mapping = UUID.randomUUID().toString();
        }
    }

    public OrderEntity(){}

    public ProductVariantEntity getProductVariant(){
        return productVariant;
    }

    public void setProductVariant(ProductVariantEntity productVariant){
        this.productVariant = productVariant;
    }

    public Integer getId(){
        return id;
    }

    public UUID getUuid(){
        return uuid;
    }

    public Instant getCreatedDate(){
        return createdDate;
    }

    public Instant getDeletedDate(){
        return deletedDate;
    }

    public void setDeletedDate(Instant deletedDate){
        this.deletedDate = deletedDate;
    }

    public String getMapping(){
        return mapping;
    }

    public void setMapping(String mapping){
        this.mapping = mapping;
    }
}
