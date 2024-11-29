package demo.ecommerce.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

import demo.ecommerce.api.ApiError;
import demo.ecommerce.product.dtos.CreateProductVariantDto;
import demo.ecommerce.product.dtos.ProductDto;
import demo.ecommerce.product.entities.ProductEntity;
import demo.ecommerce.product.entities.ProductVariantEntity;

@RestController
public class ProductController {

  @Autowired
  private ProductService productService;

  ProductController(ProductService productService){
    this.productService = productService;
  }

	@GetMapping("/products")
  public List<ProductEntity> getProducts() {
    return this.productService.getProducts();
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

	@GetMapping("/product-variants")
  public List<ProductVariantEntity> getProductVariants(@RequestParam String searchTerm) {
    return this.productService.getProductVariants(searchTerm);
  }

  @PostMapping("/product-variants")
  public ResponseEntity<?> createProductVariant(
    @RequestBody CreateProductVariantDto createProductVariantDto
  ) {
      try {
        ProductVariantEntity productVariant = this.productService.createProductVariant(createProductVariantDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productVariant);
      } catch(IllegalArgumentException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
          new ApiError(
            HttpStatus.CONFLICT.value(), 
            "Conflict", e.getMessage(), 
            "@Post /products/"+createProductVariantDto.getProductUuid()
          )
        );
      }
  }
  
}
