package scs.iss.agentie.entities;

import com.sun.javafx.beans.IDProperty;

import javax.annotation.Generated;
import javax.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String productName;

    @Column
    private Integer amount;

    @Column
    private Float price;

    public Product(Integer id, String productName, Integer amount, Float price) {
        this.id = id;
        this.productName = productName;
        this.amount = amount;
        this.price = price;
    }

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
