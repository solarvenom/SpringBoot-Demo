package demo.ecommerce.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.ecommerce.product.dtos.CreateProductVariantDto;
import demo.ecommerce.product.dtos.ProductDto;
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
   
    public List<ProductVariantEntity> getProducts(String searchTerm) {
        if(searchTerm != null){
            return this.productVariantRepository.findBySearchTerm(searchTerm);
        } else {
            return this.productVariantRepository.findAll();
        }
    }

    public ProductEntity createProduct(ProductDto productDto){
        if(productRepository.existsByName(productDto.getName())){
            throw new IllegalArgumentException("A product with this name already exists.");
        }
        ProductEntity product = new ProductEntity(productDto.getName(), productDto.getDescription());
        return productRepository.save(product);
    }

    public ProductVariantEntity createProductVariant(
        UUID productUuid,
        CreateProductVariantDto createProductVariantDto
    ){
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
}
