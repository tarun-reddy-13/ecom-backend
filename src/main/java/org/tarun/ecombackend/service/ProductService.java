package org.tarun.ecombackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.tarun.ecombackend.model.Product;
import org.tarun.ecombackend.repo.ProductRepo;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepo productRepo;

    @Autowired
    public void setProductRepo(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }

    public  Product getProduct(int productId){
        Optional<Product> p = productRepo.findById(productId);
        return p.orElse(new Product(-1));
    }

    @Transactional(readOnly = true)
    public List<Product> getProductsByKeyword(String keyword){
        return productRepo.findByNameContainingOrDescriptionContainingOrBrandContainingOrCategoryContaining(keyword, keyword, keyword, keyword);
    }

    public Product addProduct(Product product, MultipartFile image) throws IOException {
        product.setImageName(image.getOriginalFilename());
        product.setImagetype(image.getContentType());
        product.setImageData(image.getBytes());
        return productRepo.save(product);
    }

    public Product updateProduct(Product product, MultipartFile image) throws IOException {
        Optional<Product> p = productRepo.findById(product.getId());
        if((p.orElse(new Product(-1))).getId()==-1)
            throw new IOException("Product not found");

        product.setImageName(image.getOriginalFilename());
        product.setImagetype(image.getContentType());
        product.setImageData(image.getBytes());

        productRepo.save(product);
        return productRepo.findById(product.getId()).orElse(new Product(-1));
    }

    public void deleteProduct(int productId){
        productRepo.deleteById(productId);
    }

}
