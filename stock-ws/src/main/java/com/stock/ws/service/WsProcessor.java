package com.stock.ws.service;

import com.stock.data.DataProcessor;
import com.stock.ws.pojo.ShowStock;
import com.stock.ws.pojo.Stock;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author waylon.mifsud
 * @since 30/09/2015
 *
 * Serves as a service layer between the rest controller
 * and the integrator and dataProcessor layers.
 */
@Service
public class WsProcessor
{
    @Autowired
    private MapperFacade mapper;
    @Autowired
    private Integrator integrator;
    @Autowired
    private DataProcessor dataProcessor;

    public boolean post(Stock stock)
    {
        return integrator.postStock(mapper.map(stock, com.stock.data.entity.Stock.class));
    }

    public ShowStock getLastStock()
    {
        return mapper.map(dataProcessor.getLastStock(), ShowStock.class);
    }
}
