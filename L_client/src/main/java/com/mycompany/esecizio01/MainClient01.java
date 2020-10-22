/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.esecizio01;/**
 *
 * @author Luca
 */
public class MainClient01 {
    
    public static void main(String[] args) {
        Client_Mqtt.getInstance().connect();
        Client_Mqtt.getInstance().publish("imalive", "ciao a tutti");
    }
    
}
