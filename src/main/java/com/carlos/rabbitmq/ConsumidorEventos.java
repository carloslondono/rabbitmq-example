package com.carlos.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

public class ConsumidorEventos {

    public static final String EVENTOS = "eventos";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EVENTOS, BuiltinExchangeType.FANOUT);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EVENTOS, "");
        channel.basicConsume(queueName, true, (consumerTag, message) -> {
            String messageBody = new String(message.getBody(), Charset.defaultCharset());
            System.out.println("Mensaje: " + messageBody);
        }, consumerTag -> {
            System.out.println("Consumidor: " + consumerTag + " cancelado");
        });
    }
}
