package demo.ecommerce.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import demo.ecommerce.api.ApiError;
import demo.ecommerce.product.dtos.ProductDto;
import demo.ecommerce.product.entities.ProductEntity;
import demo.ecommerce.product.entities.ProductVariantEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ProductController {

  @Autowired
  private ProductService productService;

  ProductController(ProductService productService){
    this.productService = productService;
  }

	@GetMapping("/products")
  public List<ProductVariantEntity> getProducts(@RequestParam String searchTerm) {
    return this.productService.getProducts(searchTerm);
  }

  @PostMapping("/products")
  public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto) {
      try {
        ProductEntity createdProduct = this.productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
      } catch(IllegalArgumentException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
          new ApiError(HttpStatus.CONFLICT.value(), "Conflict", e.getMessage(), "@Post /products")
        );
      }
  }
}
