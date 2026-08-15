package org.tarun.ecombackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "brand")
    private String brand;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "category")
    private String category;

    @Column(name = "releasedate")
    private Date releaseDate;

    @Column(name = "productavailable")
    private boolean productAvailable;

    @Column(name = "stockquantity")
    private int stockQuantity;

    @Column(name="imagename")
    private String imageName;

    @Column(name = "imagetype")
    private String imagetype;

    @Lob
    @Column(name = "imagedata")
    private byte[] imageData;

    public Product(int productId){
        this.setId(productId);
    }

    @Override
    public String toString() {
        return "Product{" +
                "imagetype='" + imagetype + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", releaseDate=" + releaseDate +
                ", productAvailable=" + productAvailable +
                ", stockQuantity=" + stockQuantity +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
