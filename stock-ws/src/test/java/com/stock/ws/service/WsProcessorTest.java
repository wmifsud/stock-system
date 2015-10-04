package com.stock.ws.service;

import com.stock.StockWsApplication;
import com.stock.data.DataProcessor;
import com.stock.ws.gateway.GatewayService;
import com.stock.ws.pojo.Stock;
import com.stock.ws.pojo.StockType;
import com.stock.ws.processor.WsProcessor;
import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author Waylon.Mifsud
 * @since 03/10/2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StockWsApplication.class)
public class WsProcessorTest {

    @Mock
    private GatewayService gatewayService;
    @Mock
    private DataProcessor dataProcessor;
    @InjectMocks
    private WsProcessor wsProcessorMock;

    @Autowired
    WsProcessor wsProcessor;

    @Rule
    public ExpectedException thrownException = ExpectedException.none();

    /**
     * Test asserts that a timeout message is returned;
     */
    @Test(expected = RuntimeException.class)
    public void postStockTimeout() {
        Stock stock = new Stock(1L, StockType.AMZO);
        Mockito.when(gatewayService.postStock(Mockito.any())).thenReturn(null);
        wsProcessorMock.post(new Stock());
        thrownException.expectMessage(WsProcessor.TIMEOUT_MSG + stock);
    }

    /**
     * Method asserts caching is working as expected.
     */
    @Test
    public void testCache() {
        //before and after are used to identify how long a request took.
        LocalDateTime before;
        LocalDateTime after;
        wsProcessor.post(new Stock(1L, StockType.AMZO));
        // since the cache is empty at this time,
        // the data processor will be required to call
        // the repository and fetch the last record from the database.
        before = LocalDateTime.now();
        wsProcessor.getLastStock();
        after = LocalDateTime.now();
        // this fetch will take longer than 0 millis since the database was queried.
        System.out.println("Invalid cache, time taken: " + ChronoUnit.MILLIS.between(before, after));
        TestCase.assertTrue(ChronoUnit.MILLIS.between(before, after) > 10);
        before = LocalDateTime.now();
        wsProcessor.getLastStock();
        after = LocalDateTime.now();
        // this time the cache was not empty and the
        // stock was retrieved from the cache instead of the database.
        // to fetch from the cache takes negligible time.
        System.out.println("Valid cache, time taken: " + ChronoUnit.MILLIS.between(before, after));
        TestCase.assertTrue(ChronoUnit.MILLIS.between(before, after) < 10);

        before = LocalDateTime.now();
        wsProcessor.getLastStock();
        after = LocalDateTime.now();
        // cache is valid, so cache is queried instead of calling the database.
        System.out.println("Valid cache, time taken: " + ChronoUnit.MILLIS.between(before, after));
        TestCase.assertTrue(ChronoUnit.MILLIS.between(before, after) < 10);
    }
}