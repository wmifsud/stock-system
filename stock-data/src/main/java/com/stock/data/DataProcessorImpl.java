package com.stock.data;

import com.stock.data.entity.Stock;
import com.stock.data.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author waylon.mifsud
 * @since 29/09/2015
 *
 * Data layer making use of the repository class
 * to store/retrieve data from the database.
 */
@Slf4j
@Service
public class DataProcessorImpl implements DataProcessor
{
    @Autowired
    private StockRepository stockRepository;

    /**
     * Method called to store the Stock entity to the database.
     * @param stock to be stored in the database.
     */
    @Override
    public void persist(Stock stock)
    {
        log.info("Storing {} in database", stock);
        stockRepository.save(stock);
    }

    /**
     * Method called to retrieved the last record id
     * which was persisted into the database.
     * @return {link Long}
     */
    @Override
    public long getLastValue()
    {
        Stock stock = stockRepository.findByMaxId();
        log.info("Retrieved the following stock with lastId: {}", stock);
        return stock == null ? 0 : stock.getId();
    }
}
