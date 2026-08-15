package org.tarun.ecombackend.controlller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tarun.ecombackend.model.Product;
import org.tarun.ecombackend.service.ProductService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        //return productService.getAllProducts();
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable int productId){
        Product product = productService.getProduct(productId);

        if(product.getId() == -1)
            return new ResponseEntity<>(product, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
        Product product = productService.getProduct(productId);

        if(product.getId() == -1)
            return new ResponseEntity<>(new byte[1], HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(product.getImageData(), HttpStatus.OK);
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> getProductsByKeyword(@RequestParam String keyword){
        return new ResponseEntity<>(productService.getProductsByKeyword(keyword), HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        try {
            Product savedProduct = productService.addProduct(product, imageFile);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        }catch (IOException ie){
            return new ResponseEntity<>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<?> updateProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        try {
            Product savedProduct = productService.updateProduct(product, imageFile);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        }catch (IOException ie){
            return new ResponseEntity<>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable int productId){
        Product product = productService.getProduct(productId);
        if(product.getId()==-1)
            return new ResponseEntity<>("Deletion failed. Product not found", HttpStatus.NOT_FOUND);

        productService.deleteProduct(productId);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
