/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.esecizio02;

import com.mycompany.esecizio02.gui.NewJDialog;
import com.mycompany.esecizio02.logic.mqtt.Client_Mqtt;
import javax.swing.JFrame;

/**
 *
 * @author Luca
 */
public class MainClient02 {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJDialog(new JFrame(), true).setVisible(true);
                System.exit(0);
            }

        });
    }
}
