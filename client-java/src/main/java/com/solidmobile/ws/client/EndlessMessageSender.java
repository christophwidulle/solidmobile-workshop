package com.solidmobile.ws.client;

import com.solidmobile.client.SolidClient;
import com.solidmobile.mds.protocol.message.Messages;
import com.solidmobile.mds.protocol.message.PayloadMessage;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Christoph Widulle
 */
public class EndlessMessageSender {

    private final SolidClient solidClient;

    public EndlessMessageSender(SolidClient solidClient) {
        this.solidClient = solidClient;
    }


    public void start() {

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        send();
                    }
                }, 1, 1, TimeUnit.SECONDS
        );


    }

    private void send() {
        PayloadMessage endlessPingMsg = Messages.builder().payloadMessage()
                .payloadType("endlessPing")
                .payload(new Date().toString())
                .qos().exactlyOnce().build();


        solidClient.services().getTransportService().send(endlessPingMsg);
    }
}
