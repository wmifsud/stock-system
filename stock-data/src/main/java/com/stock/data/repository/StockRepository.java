package com.stock.data.repository;

import com.stock.data.entity.Stock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * @author waylon.mifsud
 * @since 27/09/2015
 *
 * Interface used to persist Stock object in the database.
 */
public interface StockRepository extends CrudRepository<Stock, Long>, QueryDslPredicateExecutor<Stock> {

}
