package com.stock.ws.pojo;

/**
 * @author Waylon.Mifsud
 * @since 01/10/2015
 *
 * The object which will be returned by the rest
 * controller when querying for the last id.
 */
public class ShowStock extends Stock {
    private Long id;

    public ShowStock() {
    }

    public ShowStock(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
