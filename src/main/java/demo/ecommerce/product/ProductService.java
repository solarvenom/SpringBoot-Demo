package demo.ecommerce.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.ecommerce.product.dtos.CreateProductVariantDto;
import demo.ecommerce.product.dtos.ProductDto;
import demo.ecommerce.product.dtos.UpdateProductDto;
import demo.ecommerce.product.entities.ProductEntity;
import demo.ecommerce.product.entities.ProductVariantEntity;
import demo.ecommerce.product.repositories.ProductRepository;
import demo.ecommerce.product.repositories.ProductVariantRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;
   
    public List<ProductEntity> getProducts() {
        return this.productRepository.findAll();
    }

    public ProductEntity createProduct(ProductDto productDto){
        if(productRepository.existsByName(productDto.getName())){
            throw new IllegalArgumentException("A product with this name already exists.");
        }
        ProductEntity product = new ProductEntity(productDto.getName(), productDto.getDescription());
        return productRepository.save(product);
    }

    public ProductEntity updateProduct(UUID uuid, UpdateProductDto productDto){
        if(!this.productRepository.existsByUuid(uuid)){
            throw new IllegalArgumentException("Product with UUID " + uuid + " does not exist.");
        }
        ProductEntity product = this.productRepository.findByUuid(uuid);
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        return this.productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(UUID uuid){
        if(!this.productRepository.existsByUuid(uuid)){
            throw new IllegalArgumentException("Product with UUID " + uuid + " does not exist.");
        }
        this.productRepository.deleteByUuid(uuid);
    }

    public List<ProductVariantEntity> getProductVariants(String searchTerm) {
        if(searchTerm != null){
            return this.productVariantRepository.findBySearchTerm(searchTerm);
        }
        return this.productVariantRepository.findAll();
    }

    public List<ProductVariantEntity> getVariantsByProduct(UUID uuid){
        if(!this.productRepository.existsByUuid(uuid)){
            throw new IllegalArgumentException("Product with UUID " + uuid + " does not exist.");
        }
        return this.productVariantRepository.findByProductUuid(uuid);
    }

    public ProductVariantEntity createProductVariant(
        CreateProductVariantDto createProductVariantDto
    ){
        UUID productUuid = createProductVariantDto.getProductUuid();

        if(productVariantRepository.existsBySizeAndColourAndPrice(
            productUuid, 
            createProductVariantDto.getSize(),
            createProductVariantDto.getColour(),
            createProductVariantDto.getPrice()
        )){
            throw new IllegalArgumentException("A product variant with this size, colour and price exists.");
        }

        ProductEntity product = this.productRepository.findByUuid(productUuid);
        ProductVariantEntity productVariant = new ProductVariantEntity(
            createProductVariantDto.getColour(),
            createProductVariantDto.getSize(),
            createProductVariantDto.getPrice()
        );
        productVariant.setProduct(product);
        return this.productVariantRepository.save(productVariant);
    }

    @Transactional
    public void deleteProductVariant(UUID uuid){
        if(!this.productVariantRepository.existsByUuid(uuid)){
            throw new IllegalArgumentException("ProductVariant with UUID " + uuid + " does not exist.");
        }
        this.productVariantRepository.deleteByUuid(uuid);
    }
}
