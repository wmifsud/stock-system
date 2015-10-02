package com.stock.data.repository;

import com.stock.data.entity.Stock;

/**
 * @author Waylon.Mifsud
 * @since 02/10/2015
 *
 * Required to be able to call custom queries.
 */
public interface StockRepositoryCustom {
    Stock findByMaxId();
}
