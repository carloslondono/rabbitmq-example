package com.carlos.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

import java.util.concurrent.TimeoutException;

public class ProductorSimple {
    public static void main(String[] args) throws IOException, TimeoutException {
        String message = "Hola mundo";

        // Abrir conexion AMQ
        ConnectionFactory connectionFactory = new ConnectionFactory();
        try (Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel()){
            // Crear cola
            String queueName = "primer-cola";
            channel.queueDeclare(queueName, false, false, false, null);

            // Enviar mensaje al exchange ""
            channel.basicPublish("", queueName, null, message.getBytes());
        }
    }
}
