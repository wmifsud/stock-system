package com.stock.ws.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Waylon.Mifsud
 * @since 01/10/2015
 *
 * The object which will be returned by the rest
 * controller when querying for the last id.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowStock extends Stock {
    private Long id;
}
