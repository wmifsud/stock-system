package com.stock.ws.controller;

import com.stock.ws.service.WsProcessor;
import com.stock.ws.pojo.Stock;
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
public class StockController {

    @Autowired
    private WsProcessor wsProcessor;

    @RequestMapping(value = "/setStock", method = RequestMethod.POST)
    public Stock setStock(@Validated @RequestBody Stock stock) {
        log.info("Received request to setStock with value: {} and type: {}", stock.getValue(), stock.getType());
        Stock savedStock = wsProcessor.post(stock);
        log.debug("Successful request for setStock with value: {}", savedStock);
        return savedStock;
    }

    @RequestMapping(value = "/getLastStock", method = RequestMethod.GET)
    public Stock getLastStock() {

        log.info("Received request to retrieve the last stock persisted");
        Stock stock = wsProcessor.getLastStock();
        log.debug("Last stock retrieved: {}", stock);
        return stock;
    }
}
