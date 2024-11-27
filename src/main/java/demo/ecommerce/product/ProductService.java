package demo.ecommerce.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.ecommerce.product.entities.ProductVariantEntity;
import demo.ecommerce.product.repositories.ProductVariantRepository;

import java.util.List;

@Service
public class ProductService {

    // @Autowired
    // private ProductRepository productRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;
   
    
    public List<ProductVariantEntity> getAllProducts() {
        List<ProductVariantEntity> allProducts = this.productVariantRepository.findAll();
        System.out.println(allProducts);
        return allProducts;
    }
}
