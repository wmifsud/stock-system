package com.stock.ws.service.helper;

import com.stock.data.entity.Stock;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

/**
 * @author Waylon.Mifsud
 * @since 29/09/2015
 *
 * Used by integration's transformer adapter to map the Stock objects.
 */
@Component
public class Converter
{
    @Autowired
    private MapperFacade mapper;

    @Transformer
    public Stock mapStock(com.stock.ws.pojo.Stock externalStock)
    {
        return mapper.map(externalStock, Stock.class);
    }
}