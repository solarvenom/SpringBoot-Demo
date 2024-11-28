package demo.ecommerce.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.ecommerce.product.dtos.ProductDto;
import demo.ecommerce.product.entities.ProductEntity;
import demo.ecommerce.product.entities.ProductVariantEntity;
import demo.ecommerce.product.repositories.ProductRepository;
import demo.ecommerce.product.repositories.ProductVariantRepository;

import java.util.List;

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
}
