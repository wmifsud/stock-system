package com.stock.ws.service.helper;

import com.stock.ws.pojo.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.stereotype.Service;

/**
 * @author waylon.mifsud
 * @since 29/09/2015
 *
 * Responsible for mapping the WS object to Entity object
 * and sends the entity to be persisted in the database.
 */
@Slf4j
@Service
public class Integrator
{
    @Autowired
    private MessageChannel inputTransformChannel;
    @Autowired
    private PollableChannel outputTransformChannel;
    @Autowired
    private MessageChannel postChannel;

    public boolean processStock(Stock stock)
    {
        return postStock(mapStock(stock));
    }

    /**
     * Method makes use of transformer to map the object from web service to entity.
     * @param stock the {@link com.stock.ws.pojo.Stock}
     * @return the mapped {@link com.stock.data.entity.Stock}
     */
    private com.stock.data.entity.Stock mapStock(Stock stock)
    {
        log.info("Mapping :{} to entity", stock);
        Message<?> message = MessageBuilder.withPayload(stock).build();
        inputTransformChannel.send(message);
        Message<?> msgReceived = outputTransformChannel.receive();
        com.stock.data.entity.Stock mappedStock =
                (com.stock.data.entity.Stock) msgReceived.getPayload();
        log.debug("Entity mapped successfully: {}", mappedStock);
        return mappedStock;
    }

    /**
     * Method posts the object to the post channel
     * @param stock the {@link com.stock.data.entity.Stock} object to post.
     * @return {@link Boolean} showing if the message was successfully sent to the queue.
     */
    private boolean postStock(com.stock.data.entity.Stock stock)
    {
        Message<?> message = MessageBuilder.withPayload(stock).build();
        return postChannel.send(message);
    }
}