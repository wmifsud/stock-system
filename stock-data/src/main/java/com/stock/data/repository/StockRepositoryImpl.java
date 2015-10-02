package com.stock.data.repository;

import com.stock.data.entity.QStock;
import com.stock.data.entity.Stock;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

/**
 * @author Waylon.Mifsud
 * @since 01/10/2015
 *
 * The implementation using QueryDSL to retrieve the last recorded stock.
 */
public class StockRepositoryImpl extends QueryDslRepositorySupport implements StockRepositoryCustom {

    public StockRepositoryImpl() {
        super(QStock.class);
    }

    @Override
    public Stock findByMaxId() {

        return from(QStock.stock).orderBy(QStock.stock.id.desc()).singleResult(QStock.stock);
    }
}
