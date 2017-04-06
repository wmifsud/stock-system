package com.stock.data;

import com.stock.data.entity.Stock;
import com.stock.data.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author waylon.mifsud
 * @since 29/09/2015
 *
 * Data layer making use of the repository class
 * to store/retrieve data from the database.
 */
@Service
public class DataProcessorImpl implements DataProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(DataProcessorImpl.class);

    @Autowired
    private StockRepository stockRepository;

    /**
     * Method called to store the Stock entity to the database.
     * @param stock to be stored in the database.
     */
    @Override
    @CacheEvict(value = "stock", allEntries = true)
    public Stock persist(Stock stock) {
        // CacheEvict invalidates the cache once new stock
        // is persisted in the database.
        LOG.info("Storing {} in database", stock);
        return stockRepository.save(stock);
    }

    /**
     * Method called to retrieved the last recorded stock
     * which was persisted into the database.
     * @return {link Long}
     */
    @Override
    @Cacheable("stock")
    public Stock getLastStock() {
        // We need to cache this result so that unnecessary calls to query
        // the last stock can be avoided.
        Stock stock = stockRepository.findByMaxId();
        LOG.info("Retrieved the following stock with maxId from database: {}", stock);
        return stock;
    }
}
