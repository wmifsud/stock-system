package com.stock.ws.service;

import com.stock.data.DataProcessor;
import com.stock.ws.gateway.GatewayService;
import com.stock.ws.pojo.ShowStock;
import com.stock.ws.pojo.Stock;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @author waylon.mifsud
 * @since 30/09/2015
 *
 * Serves as a service layer between the rest controller
 * and the integrator and dataProcessor layers.
 */
@Service
@EnableCaching
public class WsProcessor {
    @Autowired
    private MapperFacade mapper;
    @Autowired
    private GatewayService gatewayService;
    @Autowired
    private DataProcessor dataProcessor;

    /**
     * Posts the stock to the gateway which
     * directs it to the appropriate channel.
     * @param stock {@link Stock} the stock to be stored.
     * @return {@link Stock} with the new id provided by the database.
     */
    public Stock post(Stock stock) {
        return mapper.map(
                gatewayService.postStock(
                        mapper.map(stock, com.stock.data.entity.Stock.class)), ShowStock.class);
    }

    /**
     * Retrieves last stock from the database.
     * @return {@link Stock}
     */
    public Stock getLastStock() {
        return mapper.map(dataProcessor.getLastStock(), ShowStock.class);
    }

    /**
     * Bean caching the stock which was stored in the database.
     */
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("stock");
    }
}
