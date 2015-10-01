package com.stock.data.repository.query;

import com.stock.data.entity.QStock;
import com.stock.data.entity.Stock;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * @author Waylon.Mifsud
 * @since 01/10/2015
 *
 * The implementation using QueryDSL to retrieve the last recorded stock.
 */
@Repository
public class QueryStockRepositoryImpl extends QueryDslRepositorySupport implements QueryStockRepository {

    public QueryStockRepositoryImpl() {
        super(QStock.class);
    }

    @Override
    public Stock findByMaxId() {

        Stock stock = null;
        //retrieve the max id from the database.
        Long maxId = from(QStock.stock).singleResult(QStock.stock.id.max());
        if (maxId != null) {
            //if found, retrieve the stock object.
            stock = from(QStock.stock).where(QStock.stock.id.eq(maxId)).singleResult(QStock.stock);
        }
        return stock;
    }
}
