package com.stock.ws.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author waylon.mifsud
 * @since 26/09/2015
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock
{
    @Min(value = 0, message = "value must be greater than or equal to 0")
    @Max(value = 1000, message = "value must be less than or equal to 1000")
    @NotNull(message = "value must not be null")
    private Long value;
    @NotNull(message = "type must not be null")
    private StockType type;
}
