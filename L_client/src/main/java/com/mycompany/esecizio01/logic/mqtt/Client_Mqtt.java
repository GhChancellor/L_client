/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.esecizio01.logic.mqtt;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author lele
 */
public class Client_Mqtt implements MqttCallback {

    private static Client_Mqtt instance = null;
    private final int qos = 2;
    private MqttClient sampleClient = null;

    private String broker = "tcp://0.0.0.0:1883";
    private String clientId = "Client : " + new Date().getTime();
    
    public static Client_Mqtt getInstance() {
        if (instance == null) {
            instance = new Client_Mqtt();
        }
        return instance;
    }

    /* inizializza il client */
    private Client_Mqtt() {
        super();

        try {
            sampleClient = new MqttClient(broker, clientId, new MemoryPersistence());
        } catch (MqttException ex) {
            Logger.getLogger(Client_Mqtt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * inizializza il client e accede a un canale 
     */
    private void initializeConnection() {
        try {
            /* inizializza il client */
            if (sampleClient == null) {
                sampleClient = new MqttClient(broker, clientId, new MemoryPersistence());
            }

            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);

            /* Conneting to Broker */
            sampleClient.connect(connectOptions);
            System.out.println("Connected to broker" + broker );
            
            /* subscribe section */
            sampleClient.subscribe("UserConnected");
            sampleClient.subscribe("Talk");
            sampleClient.setCallback(this);
            
            publish("UserConnected", "is connected\n");

            
        } catch (Exception e) {
            Logger.getLogger(Client_Mqtt.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void connect() {
        initializeConnection();        
    }

    /**
     * Public message
     * @param topic
     * @param message 
     */
    public void publish(String topic, String message) {
        String _message = clientId + " " + message;
                
        
        try {
            MqttMessage messageMM = new MqttMessage(_message.getBytes());
            messageMM.setQos(qos);
            
            if (sampleClient == null || !sampleClient.isConnected()) {
                initializeConnection();
            }
            
            sampleClient.publish(topic, messageMM);
        } catch (Exception ex) {
            Logger.getLogger(Client_Mqtt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void connectionLost(Throwable thrwbl) {

    }

    /**
     * Riceve un messaggio dal server e lo mette a video
     * Dal server metodo public
     * @param topic
     * @param mm
     * @throws Exception 
     */
    @Override
    public void messageArrived(String topic, MqttMessage mm) throws Exception {
        System.out.println("TOPIC: " + topic);
        System.out.println("MESSAGE: " + new String(mm.getPayload()) + "\n");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {

    }

}
