package com.magerramov.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement()
public class Products {

    List<Product> products = new ArrayList<>();

    public Products(List<Product> products) {
        this.products = products;
    }

    public Products() {
    }

    public List<Product> getProducts() {
        return products;
    }

    @XmlElement(name = "PRODUCT")
    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
