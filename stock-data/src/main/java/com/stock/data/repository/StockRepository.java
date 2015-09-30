package com.stock.data.repository;

import com.stock.data.entity.Stock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author waylon.mifsud
 * @since 27/09/2015
 */
public interface StockRepository extends CrudRepository<Stock, Long>
{
    String MAX_ID_QUERY = "select max(s) from Stock s";

    @Query(MAX_ID_QUERY)
    Stock findByMaxId();
}
