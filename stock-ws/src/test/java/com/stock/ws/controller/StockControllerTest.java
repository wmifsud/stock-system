package com.stock.ws.controller;

import com.stock.StockWsApplication;
import com.stock.Utils;
import com.stock.ws.pojo.Stock;
import com.stock.ws.pojo.StockType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * @author waylon.mifsud
 * @since 30/09/2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StockWsApplication.class)
@WebAppConfiguration
public class StockControllerTest
{
    @Autowired
    public WebApplicationContext wac;

    public MockMvc mockMvc;

    public static final String SETTER_URL = "/setStock";
    public static final String GETTER_URL = "/getLastStock";
    public static final Long CORRECT_VALUE = 10L;
    public static final Long INCORRECT_MAX_VALUE = 1001L;
    public static final Long INCORRECT_MIN_VALUE = -1L;
    public static final String INCORRECT_MAX_VALUE_MSG = "value must be less than or equal to 1000";
    public static final String INCORRECT_MIN_VALUE_MSG = "value must be greater than or equal to 0";
    public static final String NULL_VALUE_MSG = "value must not be null";
    public static final String NULL_TYPE_MSG = "type must not be null";
    public static final String STOCK_PERSISTED = "true";
    //required to keep track of last record which was
    // persisted in the database so that proper assertions can be
    // carried out on the last id retrieved by the data processor.
    public static int lastId;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * Tests a successful posting of the method.
     * @throws Exception {@link MockMvc} can throw exception.
     */
    @Test
    public void setStockTest() throws Exception {
        Stock stock = new Stock(CORRECT_VALUE, StockType.AMZO);
        mockMvc.perform(post(SETTER_URL)
                .content(Utils.convertToJsonString(stock))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(STOCK_PERSISTED))
                .andExpect(status().isOk());
        lastId++;
    }

    /**
     * Tests that a bad request is returned with the null value message.
     * @throws Exception {@link MockMvc} can throw exception.
     */
    @Test
    public void setStockTestNullValue() throws Exception {
        Stock stock = new Stock(null, StockType.AMZO);
        mockMvc.perform(post(SETTER_URL)
                .content(Utils.convertToJsonString(stock))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(NULL_VALUE_MSG))
                .andExpect(status().isBadRequest());
    }

    /**
     * Tests that a bad request is returned with the null value message.
     * @throws Exception {@link MockMvc} can throw exception.
     */
    @Test
    public void setStockTestNullType() throws Exception {
        Stock stock = new Stock(CORRECT_VALUE, null);
        mockMvc.perform(post(SETTER_URL)
                .content(Utils.convertToJsonString(stock))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(NULL_TYPE_MSG))
                .andExpect(status().isBadRequest());
    }

    /**
     * Tests that a bad request is returned with the incorrect max value message.
     * @throws Exception {@link MockMvc} can throw exception.
     */
    @Test
    public void setStockTestIncorrectMaxValue() throws Exception {
        Stock stock = new Stock(INCORRECT_MAX_VALUE, StockType.AMZO);
        mockMvc.perform(post(SETTER_URL)
                .content(Utils.convertToJsonString(stock))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(INCORRECT_MAX_VALUE_MSG))
                .andExpect(status().isBadRequest());
    }

    /**
     * Tests that a bad request is returned with the incorrect min value message.
     * @throws Exception {@link MockMvc} can throw exception.
     */
    @Test
    public void setStockTestIncorrectMinValue() throws Exception {
        Stock stock = new Stock(INCORRECT_MIN_VALUE, StockType.AMZO);
        mockMvc.perform(post(SETTER_URL)
                .content(Utils.convertToJsonString(stock))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(INCORRECT_MIN_VALUE_MSG))
                .andExpect(status().isBadRequest());
    }

    /**
     * Tests that the one and only stock is returned after one persist.
     * @throws Exception {@link MockMvc} can throw exception.
     */
    @Test
    @Transactional
    public void getLastStockIdTestWith1Persist() throws Exception
    {
        Stock stock = new Stock(CORRECT_VALUE, StockType.AMZO);
        mockMvc.perform(post(SETTER_URL)
                        .content(Utils.convertToJsonString(stock))
                        .contentType(MediaType.APPLICATION_JSON));
        lastId++;
        mockMvc.perform(get(GETTER_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{\"value\":10,\"type\":\"AMZO\",\"id\":"+ lastId +"}"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    /**
     * Tests that the last stock is returned after persisting 3 stocks
     * @throws Exception {@link MockMvc} can throw exception.
     */
    @Test
    @Transactional
    public void getLastStockIdTestWith3Persists() throws Exception
    {
        Stock stock = new Stock(CORRECT_VALUE, StockType.AMZO);
        mockMvc.perform(post(SETTER_URL)
                .content(Utils.convertToJsonString(stock))
                .contentType(MediaType.APPLICATION_JSON));
        lastId++;
        stock = new Stock(CORRECT_VALUE, StockType.GOOG);
        mockMvc.perform(post(SETTER_URL)
                .content(Utils.convertToJsonString(stock))
                .contentType(MediaType.APPLICATION_JSON));
        lastId++;
        stock = new Stock(CORRECT_VALUE, StockType.APPL);
        mockMvc.perform(post(SETTER_URL)
                .content(Utils.convertToJsonString(stock))
                .contentType(MediaType.APPLICATION_JSON));
        lastId++;

        mockMvc.perform(get(GETTER_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{\"value\":10,\"type\":\"APPL\",\"id\":"+ lastId +"}"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
