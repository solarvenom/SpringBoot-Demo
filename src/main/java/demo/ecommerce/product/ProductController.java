package demo.ecommerce.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

import demo.ecommerce.api.errors.ApiError;
import demo.ecommerce.product.dtos.CreateProductVariantDto;
import demo.ecommerce.product.dtos.ProductDto;
import demo.ecommerce.product.dtos.UpdateProductDto;
import demo.ecommerce.product.entities.ProductEntity;
import demo.ecommerce.product.entities.ProductVariantEntity;

@RestController
public class ProductController {

  @Autowired
  private ProductService productService;
  ProductController(ProductService productService){
    this.productService = productService;
  }

  @CrossOrigin(origins = "*")
	@GetMapping("/products")
  public List<ProductEntity> getProducts() {
    return this.productService.getProducts();
  }

  @CrossOrigin(origins = "*")
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

  @CrossOrigin(origins = "*")
  @GetMapping("/products/{uuid}")
  public ResponseEntity<?> getVariantsByProduct(@PathVariable UUID uuid){
    try {
      List<ProductVariantEntity> productVariants = this.productService.getVariantsByProduct(uuid);
      return ResponseEntity.status(HttpStatus.OK).body(productVariants);
    } catch(IllegalArgumentException e){
      return ResponseEntity.status(HttpStatus.CONFLICT).body(
        new ApiError(HttpStatus.CONFLICT.value(), "Conflict", e.getMessage(), "@Post /products")
      );
    }
  }

  @CrossOrigin(origins = "*")
  @PutMapping("/products/{uuid}")
  public ResponseEntity<?> updateProduct(
    @PathVariable UUID uuid,
    @RequestBody UpdateProductDto updateProductDto
  ) {
    try {
      ProductEntity updatedProduct = this.productService.updateProduct(uuid, updateProductDto);
      return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    } catch(IllegalArgumentException e){
      return ResponseEntity.status(HttpStatus.CONFLICT).body(
        new ApiError(HttpStatus.CONFLICT.value(), "Conflict", e.getMessage(), "@Put /products/" + uuid)
      );
    }
  }

  @CrossOrigin(origins = "*")
  @DeleteMapping("/products/{uuid}")
  public ResponseEntity<?> deleteProduct(
    @PathVariable UUID uuid,
    @RequestBody UpdateProductDto updateProductDto
  ) {
    try {
      this.productService.deleteProduct(uuid);
      return ResponseEntity.status(HttpStatus.OK).body("Product deleted.");
    } catch(IllegalArgumentException e){
      return ResponseEntity.status(HttpStatus.CONFLICT).body(
        new ApiError(HttpStatus.CONFLICT.value(), "Conflict", e.getMessage(), "@Delete /products/" + uuid)
      );
    }
  }

  @CrossOrigin(origins = "*")
	@GetMapping("/product-variants")
  public List<ProductVariantEntity> getAllProductVariants(@RequestParam String searchTerm) {
    return this.productService.getProductVariants(searchTerm);
  }

  @CrossOrigin(origins = "*")
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

  @CrossOrigin(origins = "*")
  @DeleteMapping("/product-variants/{uuid}")
  public ResponseEntity<?> deleteProductVariant(@PathVariable UUID uuid){
    try {
      this.productService.deleteProductVariant(uuid);
      return ResponseEntity.status(HttpStatus.OK).body("ProductVariant deleted.");
    } catch(IllegalArgumentException e){
      return ResponseEntity.status(HttpStatus.CONFLICT).body(
        new ApiError(HttpStatus.CONFLICT.value(), "Conflict", e.getMessage(), "@Delete /product-variants/" + uuid)
      );
    }
  }
  
}
