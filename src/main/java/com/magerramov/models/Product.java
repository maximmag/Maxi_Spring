package com.magerramov.models;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "Sale_Product")
@XmlRootElement
@XmlType(propOrder = {
        "id",
        "productCode",
        "name",
        "priceXml",
        "price",
        "count",
        "sale"})
public class Product {

    @XmlTransient
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sale_SEQUENCE")
    private long id;

    private long productCode;
    private String name;
    @Transient
    private String priceXml;

    private double price;
    private int count;

    @XmlTransient
    @ManyToOne(fetch = FetchType.LAZY)
    private Sale sale;

    public Product() {
    }


    @XmlElement(name = "PRODUCT_CODE")
    public void setProductCode(long productCode) {
        this.productCode = productCode;
    }

    @XmlElement(name = "NAME")
    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "PRICE")
    public void setPriceXml(String priceXml) {
        this.priceXml = priceXml;
    }

    @XmlElement(name = "COUNT")
    public void setCount(int count) {
        this.count = count;
    }


    public long getProductCode() {
        return productCode;
    }

    public String getName() {
        return name;
    }


    public int getCount() {
        return count;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPriceXml() {
        return priceXml;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
