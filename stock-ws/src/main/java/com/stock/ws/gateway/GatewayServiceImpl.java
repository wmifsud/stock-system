package com.stock.ws.gateway;

import com.stock.data.DataProcessor;
import com.stock.data.entity.Stock;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Waylon.Mifsud
 * @since 02/10/2015
 */
public class GatewayServiceImpl implements GatewayService {

    @Autowired
    private DataProcessor dataProcessor;

    public Stock postStock(Stock stock) {
        return dataProcessor.persist(stock);
    }
}
