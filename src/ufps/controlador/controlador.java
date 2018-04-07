
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.controlador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import ufps.client.chat;
import ufps.cliente.DTO.Message;
import ufps.gui.JFchat;
import ufps.gui.JFchat1;

/**
 *
 * @author Jesus
 */
public class controlador extends Thread {
   private chat chat;
   private String nombre="";
   private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   private boolean activo=true;
   private JFchat1 ventana;
 
   
 public controlador(String nombre,JFchat1 ventana,String host){
   this.chat=new chat(nombre,this,host);
   this.ventana=ventana;
 }

    public controlador() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
   @Override
 public void run(){
      
          while(activo){ 
              System.out.print("");
              if (this.chat.getContectados()) {
                  
              }
        }
 }
 
//Metodo para enviar Mensaje al destino 
 public void enviarMensaje(String mensaje,String nombre ){
     this.setNombre(nombre);
     
       try {
           this.chat.enviarMensaje(mensaje, nombre);
       } catch (IOException ex) {
          System.out.println("Error al enviar mensaje");
       }
     
 }
 
 //Metodo que muestra los conectados JFrame
 public void conectados(String cad){
     this.ventana.mostraConectados(cad);
 }
 
 public void seleccionarSala(ArrayList<Message> mensajes){
     this.ventana.seleccionarSala(mensajes);
 }
 
 public void requestMessage() {
        try {
            System.out.print("\n" + "Nuevo Mensaje:" + "");
            String mensaje = br.readLine();
            if (mensaje.contains(",")) {
                String[] dataMessage = mensaje.split(",");
                enviarMensaje(dataMessage[1], dataMessage[0]);
            } else {
                System.out.println("Formato de chat incorrecto.");
                this.requestMessage();
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

 public void desconectar(){
     this.chat.desconectar();
 }
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void cambiarSala(String sala) {
       this.chat.cambiarSala(sala);
    }

    public void mostrarMensaje(Message mensaje) {
        this.ventana.ingresarMensaje(mensaje);
    }
 
 
    
 
 
 
}
