package com.magerramov.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;


@XmlRootElement(name = "SALES")
public class Sales {
    private ArrayList<Sale> salesList = new ArrayList<Sale>();

    public Sales(ArrayList<Sale> salesList) {
        this.salesList = salesList;
    }

    public Sales() {
    }

    @XmlElement(name = "SALE")
    public void setSalesList(ArrayList<Sale> salesList) {
        this.salesList = salesList;
    }

    public ArrayList<Sale> getSalesList() {
        return salesList;
    }

    public boolean addSale(Sale sale){
        return salesList.add(sale);
    }
}
