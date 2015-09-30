package com.stock.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author waylon.mifsud
 * @since 27/09/2015
 */
@Data
@Entity
@NoArgsConstructor
public class Stock
{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Long value;
    private StockType type;
}
