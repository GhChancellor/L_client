/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.esecizio02.logic.mqtt;

import com.mycompany.esecizio02.logic.Dispatcher;
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
    private MqttClient client = null;

    private String broker = "tcp://0.0.0.0:1883";
    private String clientId = "" + new Date().getTime();
    private final String userConnected = "UserConnected";

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
            client = new MqttClient(broker, clientId, new MemoryPersistence());
        } catch (MqttException ex) {
            Logger.getLogger(Client_Mqtt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void connect() {
        initializeConnection();
    }

    /**
     * inizializza il client e accede a un canale
     */
    private void initializeConnection() {
        try {
            if (client == null) {
                client = new MqttClient(broker, clientId, new MemoryPersistence());
            }

            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);

            /* Conneting to Broker */
            client.connect(connectOptions);

            /* Public message */
            publish("UserConnected", "Client " + clientId + " is connected. " + "\n");
       
            /* subscribe section */
            client.subscribe(userConnected);     
            client.subscribe("allusers");
            client.setCallback(this);
            
        } catch (Exception ex) {
            Logger.getLogger(Client_Mqtt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void topicSubscribe(String topic) throws MqttException {
        client.subscribe(topic);
    }

    public void topicUnsubscribe(String topic) throws MqttException {
        client.unsubscribe(topic);
    }

    /**
     * Public message
     *
     * @param topic
     * @param message
     */
    public void publish(String topic, String message) {
        try {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(qos);

            if (client == null || client.isConnected()) {
                initializeConnection();
            }

            client.publish(topic, mqttMessage);
        } catch (MqttException ex) {
            Logger.getLogger(Client_Mqtt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void connectionLost(Throwable thrwbl) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Riceve un messaggio dal server e lo mette a video Dal server metodo
     * public
     *
     * @param topic
     * @param mm
     * @throws Exception
     */
    @Override
    public void messageArrived(String topic, MqttMessage mm) throws Exception {
        if (topic.equals(userConnected)) {
            connected(mm);
        }

        /*
        String string = "004-034556";
String[] parts = string.split("-");
String part1 = parts[0]; // 004
String part2 = parts[1]; // 034556
         */
        if (topic.equals("allusers")) {
            String[] allusers = new String( mm.getPayload()).split("-");
            for (String alluser : allusers) {
                allUsers(alluser);
                System.out.println("All users"+ alluser);
            }
            

//            client.unsubscribe("allusers");

        }

        if (topic.equals("talk")) {
            talk(mm);
        }

        System.out.println("TOPIC: " + topic);
        System.out.println("MESSAGE: " + new String(mm.getPayload()) + "\n");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void allUsers(String alluser){
        Dispatcher.getInstance().update(alluser);
    }
    
    private void connected(MqttMessage mm) {
        Dispatcher.getInstance().update(new String(mm.getPayload()));
    }

    private void talk(MqttMessage mm) {
        Dispatcher.getInstance().channel(new String(mm.getPayload()));
    }

}
