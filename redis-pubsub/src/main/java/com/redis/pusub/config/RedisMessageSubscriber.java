package com.redis.pusub.config;

import com.redis.pusub.thread.PubSubThread;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class RedisMessageSubscriber implements MessageListener {

    @Override
    public void onMessage(final Message message, final byte[] pattern) {
        processMessages(new String(message.getBody()));
    }

    public void processMessages(String message) {
        try {
            PubSubThread pubSubThread = new PubSubThread(message);
            pubSubThread.start();
        } catch (Exception e) {
            System.out.println("ERROR IN PROCESSING SUBSCRIBED MESSAGE::"+e);
        }

    }
}
