/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.esecizio02.logic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lele
 */
public class Dispatcher {
    private static Dispatcher instance = null;
    private List < Listener > listeners = new ArrayList<>();
    
    public static Dispatcher getInstance(){
        if (instance == null)
            instance = new Dispatcher();
        return instance;
    }
    
    public void addDispatcher(Listener listener){
        listeners.add(listener);
    }
    
    public void update(String message){
        for (Listener listener : listeners) {
            listener.userConnected(message);
        }
    }
    
    public void channel(String message){
        for (Listener listener : listeners) {
            listener.channel(message);
        }
    }
    
    public void allUsers(String allUsers){
        for (Listener listener : listeners) {
            listener.allUsers(allUsers);
        }
    }
}
