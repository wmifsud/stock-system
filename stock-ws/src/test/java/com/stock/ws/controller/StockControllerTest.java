package com.stock.ws.controller;

import com.google.gson.Gson;
import com.stock.StockWsApplication;
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
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * @author waylon.mifsud
 * @since 30/09/2015
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StockWsApplication.class)
public class StockControllerTest {

    @Autowired
    public WebApplicationContext wac;

    public MockMvc mockMvc;

    private static final String SETTER_URL = "/setStock";
    private static final String GETTER_URL = "/getLastStock";
    private static final Long CORRECT_VALUE = 10L;
    private static final Long INCORRECT_MAX_VALUE = 1001L;
    private static final Long INCORRECT_MIN_VALUE = -1L;
    private static final String INCORRECT_MAX_VALUE_MSG = "value must be less than or equal to 1000";
    private static final String INCORRECT_MIN_VALUE_MSG = "value must be greater than or equal to 0";
    private static final String NULL_VALUE_MSG = "value must not be null";
    private static final String NULL_TYPE_MSG = "type must not be null";
    // required to keep track of last record which was
    // persisted in the database so that proper assertions can be
    // carried out on the last id retrieved by the data processor.
    private static long lastId = 0;
    // required to give enough time to the data
    // processor to persist the last stock in the database
    // since in some rare scenarios it was returning the previous
    // stock just before persisting the new stock in the database.
    private static long sleepTime = 500;
    //Required to convert objects to json objects when sending rest requests.
    private static Gson gson = new Gson();

    private static String convertToJsonString(Object o) {
        return gson.toJson(o);
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * Required to build json string in order
     * to be able to assert the whole json object string.
     * @param stock {@link Stock} the stock which is sent to the web service.
     * @return {@link String} json string.
     */
    private String populateJsonString(Stock stock){
        return "{\"value\":" +
                stock.getValue() +
                ",\"type\":\"" +
                stock.getType() +
                "\",\"id\":" +
                lastId + "}";
    }

    /**
     * Tests a successful posting of the method.
     * @throws Exception {@link MockMvc} can throw exception.
     */
    @Test
    public void setStockTest() throws Exception {
        Stock stock = new Stock(CORRECT_VALUE, StockType.AMZO);
        lastId++;
        mockMvc.perform(post(SETTER_URL)
                .content(convertToJsonString(stock))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(populateJsonString(stock)))
                .andExpect(status().isOk());
    }

    /**
     * Tests that a bad request is returned with the null value message.
     * @throws Exception {@link MockMvc} can throw exception.
     */
    @Test
    public void setStockTestNullValue() throws Exception {
        Stock stock = new Stock(null, StockType.AMZO);
        mockMvc.perform(post(SETTER_URL)
                .content(convertToJsonString(stock))
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
                .content(convertToJsonString(stock))
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
                .content(convertToJsonString(stock))
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
                .content(convertToJsonString(stock))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(INCORRECT_MIN_VALUE_MSG))
                .andExpect(status().isBadRequest());
    }

    /**
     * Tests that the last stock is returned after one persist.
     * @throws Exception {@link MockMvc} can throw exception.
     */
    @Test
    public void getLastStockIdTestWith1Persist() throws Exception {
        Stock stock = new Stock(CORRECT_VALUE, StockType.APPL);
        mockMvc.perform(post(SETTER_URL)
                        .content(convertToJsonString(stock))
                        .contentType(MediaType.APPLICATION_JSON));
        lastId++;
        Thread.sleep(sleepTime);
        mockMvc.perform(get(GETTER_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(populateJsonString(stock)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    /**
     * Tests that the last stock is returned after persisting 3 stocks
     * @throws Exception {@link MockMvc} can throw exception.
     */
    @Test
    public void getLastStockIdTestWith3Persists() throws Exception {
        Stock stock = new Stock(CORRECT_VALUE, StockType.AMZO);
        mockMvc.perform(post(SETTER_URL)
                .content(convertToJsonString(stock))
                .contentType(MediaType.APPLICATION_JSON));
        lastId++;
        stock = new Stock(CORRECT_VALUE, StockType.GOOG);
        mockMvc.perform(post(SETTER_URL)
                .content(convertToJsonString(stock))
                .contentType(MediaType.APPLICATION_JSON));
        lastId++;
        stock = new Stock(CORRECT_VALUE, StockType.APPL);
        mockMvc.perform(post(SETTER_URL)
                .content(convertToJsonString(stock))
                .contentType(MediaType.APPLICATION_JSON));
        lastId++;
        Thread.sleep(sleepTime);
        mockMvc.perform(get(GETTER_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(populateJsonString(stock)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}