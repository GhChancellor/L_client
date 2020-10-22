/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.esecizio02.logic;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author lele
 */
public class EventManager {
    private static EventManager instance = null;
    private List< ConnectionEvent > connectionEvents = new LinkedList<>();
    
    public static EventManager getInstance(){
        if (instance == null){
            instance = new EventManager();
        }
        return instance;
    }

    public List<ConnectionEvent> getConnectionEvents() {
        return connectionEvents;
    }

    public void addConnectionEvents(ConnectionEvent connectionEvent) {
        this.connectionEvents.add(connectionEvent);
    }
    
    public void getMessage(String message){
        for (ConnectionEvent connectionEvent : connectionEvents) {
            connectionEvent.messagge(message);
        }
    }
}
