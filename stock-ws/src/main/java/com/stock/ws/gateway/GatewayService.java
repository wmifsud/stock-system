package com.stock.ws.gateway;

import com.stock.data.entity.Stock;

/**
 * @author Waylon.Mifsud
 * @since 02/10/2015
 */
public interface GatewayService {

    Stock postStock(Stock stock);
}
