/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.l_client;

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

    private String content = "Visit www.hascode.com! :D";
    private String broker = "tcp://0.0.0.0:1883";
    private String clientId = "client : " + new Date().getTime();

    public static Client_Mqtt getInstance() {
        if (instance == null) {
            instance = new Client_Mqtt();
        }
        return instance;
    }

    private Client_Mqtt() {
        super();
    }

    public void connect() {
        String topic = "news";

        try {
//            xxx();
            
            sampleClient = new MqttClient(broker, clientId, new MemoryPersistence());

            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);

            /* Conneting to Broker */
            System.out.println("Conneting to Broker" + broker);
            sampleClient.connect(connectOptions);

            sampleClient.subscribe("UserConnected");
            sampleClient.setCallback(this);

            System.out.println("Connected to broker");
//            System.out.println("paho-client publishing message: " + content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            System.out.println("Message published");

        } catch (MqttException ex) {
            ex.printStackTrace();
        }

    }

    private void xxx(){
        try {
            sampleClient = new MqttClient(broker, clientId, new MemoryPersistence());
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);

            /* Conneting to Broker */
            System.out.println("Conneting to Broker" + broker);
            sampleClient.connect(connectOptions);

            sampleClient.subscribe("UserConnected");
            sampleClient.setCallback(this);

            System.out.println("Connected to broker");
        } catch (MqttException ex) {
            Logger.getLogger(Client_Mqtt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void publish(String topic, String message){
        try {
            MqttMessage messageMM = new MqttMessage(message.getBytes());
            messageMM.setQos(qos);
            
            if (sampleClient.isConnected()){
                System.out.println("Client is connected");
            }else{
//                xxx();
                
                sampleClient = new MqttClient(broker, clientId, new MemoryPersistence());
                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(true);
                System.out.println("paho-client connecting to broker: " + broker);
                sampleClient.connect(connOpts);
                System.out.println("NOT CONNESSO");

            }
            
            sampleClient.publish(topic, messageMM);
        } catch (Exception ex) {
            Logger.getLogger(Client_Mqtt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void connectionLost(Throwable thrwbl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void messageArrived(String topic, MqttMessage mm) throws Exception {
        System.out.println("Topic " + topic);
        System.out.println("" + mm.getPayload());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
