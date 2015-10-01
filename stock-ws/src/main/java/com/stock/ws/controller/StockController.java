package com.stock.ws.controller;

import com.stock.ws.pojo.ShowStock;
import com.stock.ws.service.WsProcessor;
import com.stock.ws.pojo.Stock;
import com.stock.ws.pojo.StockType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author waylon.mifsud
 * @since 26/09/2015
 *
 * Controller for the RESTful web service.
 */
@Slf4j
@RestController
public class StockController
{
    @Autowired
    private WsProcessor wsProcessor;

    @RequestMapping(value = "/setStock", method = RequestMethod.POST)
    public Boolean setStock(@Validated @RequestBody Stock stock)
    {
        log.info("Received request to setStock with value: {} and type: {}", stock.getValue(), stock.getType());
        boolean wasSuccessful = wsProcessor.post(stock);
        log.debug("Successful request for setStock with value: {}? {}", stock.getValue(), wasSuccessful);
        return wasSuccessful;
    }

    @RequestMapping(value = "/getLastStock", method = RequestMethod.GET)
    public Stock getLastStock()
    {
        log.info("Received request to retrieve the last stock persisted");
        ShowStock showStock = wsProcessor.getLastStock();
        log.debug("Last stock retrieved: {}", showStock);
        return showStock;
    }
}
