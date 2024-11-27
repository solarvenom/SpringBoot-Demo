package demo.ecommerce.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import demo.ecommerce.product.entities.ProductVariantEntity;

@RestController
public class ProductController {

  @Autowired
  private ProductService productService;

  ProductController(ProductService productService){
    this.productService = productService;
  }

	@GetMapping("/products")
  public List<ProductVariantEntity> getAllProducts() {
    return this.productService.getAllProducts();
  }
}
