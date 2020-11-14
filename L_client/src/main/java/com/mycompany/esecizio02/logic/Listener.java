/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.esecizio02.logic;

/**
 *
 * @author lele
 */
public interface Listener {
    public void userConnected(String message);
    public void channel(String message);
    public void allUsers(String allUsers);
}
