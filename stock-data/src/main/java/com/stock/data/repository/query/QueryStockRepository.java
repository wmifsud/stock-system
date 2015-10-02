package com.stock.data.repository.query;

import com.stock.data.entity.Stock;

/**
 * @author Waylon.Mifsud
 * @since 01/10/2015
 *
 * Interface used to fetch last recorded stock.
 */
public interface QueryStockRepository {

    Stock findByMaxId();
}
