package demo.ecommerce.product.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name="products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "UUID")
    private UUID uuid;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Instant createdDate;

    @Column(updatable = true, nullable = true)
    private Instant deletedDate;

    @PrePersist
    public void generateUuid() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }

    protected ProductEntity(){}

    public ProductEntity(
        String name,
        String description
    ){
        this.name = name;
        this.description = description;
    }

    public UUID getUuid(){
        return uuid;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
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

}
