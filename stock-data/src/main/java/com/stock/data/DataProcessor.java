package com.stock.data;

import com.stock.data.entity.Stock;

/**
 * @author waylon.mifsud
 * @since 29/09/2015
 */
public interface DataProcessor {

    Stock persist(Stock stock);
    Stock getLastStock();
}
