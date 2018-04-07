/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.cliente.DTO;

/**
 *
 * @author mayeg
 */
public class Message {
    
    private String origin;
    private String destination;
    private String message;

    public Message(String origin, String destination, String message) {
        this.origin = origin;
        this.destination = destination;
        this.message = message;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
