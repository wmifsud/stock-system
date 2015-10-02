package com.stock.ws.mapper;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author waylon.mifsud
 * @since 29/09/2015
 *
 * Class required in order to use Orika with spring configuration.
 */
@Component
public class MapperFacadeFactory implements FactoryBean<MapperFacade>
{
    public MapperFacade getObject() throws Exception {
        return new DefaultMapperFactory.Builder().build().getMapperFacade();
    }

    public Class<?> getObjectType() {
        return MapperFacade.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
