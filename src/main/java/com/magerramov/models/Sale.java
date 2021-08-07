package com.magerramov.models;



import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlRootElement
@XmlType(propOrder = {
        "id",
        "cardNumber",
        "date",
        "xmlDate",
        "products",
        "productsList"})
@Entity
public class Sale {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Sale_SEQUENCE")
    @XmlTransient
    private long id;
    private long cardNumber;
    private Date date;
    @Transient
    private long xmlDate;
    @Transient
    private Products products;

    @XmlTransient
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> productsList = new ArrayList<>();
    public Sale() {
    }

    @XmlElement(name = "CARD_NUMBER")
    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    @XmlElement(name = "DATE")
    public void setXmlDate(long xmlDate) {
        this.xmlDate = xmlDate;
    }

    @XmlElement(name = "PRODUCTS")
    public void setProducts(Products products) {
        this.products = products;
    }


    public long getCardNumber() {
        return cardNumber;
    }

    public long getXmlDate() {
        return xmlDate;
    }

    public Products getProducts() {
        return products;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Product> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Product> productsList) {
        this.productsList = productsList;
    }


    public void addProduct(Product product) {
        product.setPrice(Double.parseDouble(product.getPriceXml().replace(',','.')));
        productsList.add(product);
        product.setSale(this);

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
