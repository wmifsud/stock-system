package com.stock.ws.controller;

import com.stock.ws.processor.WsProcessor;
import com.stock.ws.pojo.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeoutException;

/**
 * @author waylon.mifsud
 * @since 26/09/2015
 *
 * Controller for the RESTful web service.
 */
@RestController
public class StockController {

    private static final Logger LOG = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private WsProcessor wsProcessor;

    @RequestMapping(value = "/setStock", method = RequestMethod.POST)
    public Stock setStock(@Validated @RequestBody Stock stock) throws TimeoutException {
        LOG.info("Received request to setStock with value: {} and type: {}", stock.getValue(), stock.getType());
        Stock savedStock = wsProcessor.post(stock);
        LOG.debug("Successful request for setStock with value: {}", savedStock);
        return savedStock;
    }

    @RequestMapping(value = "/getLastStock", method = RequestMethod.GET)
    public Stock getLastStock() {

        LOG.info("Received request to retrieve the last stock persisted");
        Stock stock = wsProcessor.getLastStock();
        LOG.debug("Last stock retrieved: {}", stock);
        return stock;
    }
}
