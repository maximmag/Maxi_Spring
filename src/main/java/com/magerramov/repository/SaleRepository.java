package com.magerramov.repository;

import com.magerramov.models.Sale;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface SaleRepository extends CrudRepository<Sale,Long> {
    @Query(value = "select sum(sale_product.price*sale_product.count) from sale_product join sale on sale.id=sale_product.sale_id where date=:date",nativeQuery = true)
    Double saleSum(@Param("date") Date date);

    @Query(value = "select name, count(*) from sale join sale_product on sale.id=sale_id where card_number=:curtNum group by name order by count(*) desc limit 3",nativeQuery = true)
    List<String> getTopProduct(int curtNum);
}
