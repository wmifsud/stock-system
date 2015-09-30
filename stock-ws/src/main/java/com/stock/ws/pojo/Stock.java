package com.stock.ws.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author waylon.mifsud
 * @since 26/09/2015
 */
@Data
@AllArgsConstructor
public class Stock
{
    @Min(0)
    @Max(1000)
    @NotNull
    private Long value;
    @NotNull
    private StockType type;
}
