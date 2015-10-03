package com.stock.ws.service;

import com.stock.ws.gateway.GatewayService;
import com.stock.ws.pojo.Stock;
import com.stock.ws.pojo.StockType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Waylon.Mifsud
 * @since 03/10/2015
 */
@RunWith(MockitoJUnitRunner.class)
public class WsProcessorTest {

    @Mock
    private GatewayService gatewayService;
    @InjectMocks
    private WsProcessor wsProcessor;

    @Rule
    public ExpectedException thrownException = ExpectedException.none();

    /**
     * Test asserts that a timeout message is returned;
     */
    @Test(expected = RuntimeException.class)
    public void postStockTimeout() {
        Stock stock = new Stock(1L, StockType.AMZO);
        Mockito.when(gatewayService.postStock(Mockito.any())).thenReturn(null);
        wsProcessor.post(new Stock());
        thrownException.expectMessage(WsProcessor.TIMEOUT_MSG + stock);
    }
}
