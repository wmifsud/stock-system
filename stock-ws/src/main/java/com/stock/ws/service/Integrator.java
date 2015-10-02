package com.stock.ws.service;

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
 * Responsible for posting the entity onto the queue channel.
 */
@Slf4j
@Service
public class Integrator {
    @Autowired
    private MessageChannel postChannel;

    /**
     * Method posts the object to the post channel
     * @param stock the {@link com.stock.data.entity.Stock} object to post.
     * @return {@link Boolean} showing if the message was successfully sent to the queue.
     */
    public boolean postStock(com.stock.data.entity.Stock stock) {
        Message<?> message = MessageBuilder.withPayload(stock).build();
        return postChannel.send(message);
    }
}