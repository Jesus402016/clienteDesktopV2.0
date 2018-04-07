/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.client;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.json.JSONArray;

import org.json.JSONObject;
import ufps.cliente.DTO.Message;

import ufps.cliente.DTO.usuario;
import ufps.controlador.controlador;

/**
 *
 * @author Jesus
 */
public class chat extends Thread {

    private usuario usuario;
    private String host = "http://";
    private int puerto = 3000;
    private boolean activo;
    private int conectados;
    private controlador cont;

    private interface MessagesTypes {

        public static String CHAT = "chat";
        public static String CONNECTION = "connection_user";
        public static String SELECT_ROOM = "select_room";
        public static String DESCONECTAR="desconectar";
    };

    private interface MessageKeys {

        public static String ORIGIN = "origin";
        public static String DESTINATION = "destination";
        public static String MESSAGE = "message";
    };

    public chat(String nombre, controlador con,String host) {
        this.usuario = new usuario(nombre);
        this.cont = con;
        this.host+=host+":"+puerto;
        this.start();
    }

    @Override
    public void run() {
        try {
            Socket socket = IO.socket(host);
            socket.on(Socket.EVENT_CONNECT, (Object... args) -> {
                socket.emit(MessagesTypes.CONNECTION, getUserJson());
                
            }).on(MessagesTypes.CONNECTION, (Object... args) -> {
                String conectados = "Global,";
                for (Object user : (JSONArray) args[0]) {
                    String userString = (String) user;
                    conectados = conectados.concat(userString).concat(",");
                }
                cont.conectados(conectados);
                this.cambiarSala("Global");
                //controlador.requestMessage();
            }).on(MessagesTypes.DESCONECTAR, (Object... args) -> {
               
                //controlador.requestMessage();
            }).on(MessagesTypes.CHAT, (Object... args) -> {
                JSONObject obj = (JSONObject) args[0];
                String origin = obj.getString(MessageKeys.ORIGIN);
                String destination = obj.getString(MessageKeys.DESTINATION);
                String message = obj.getString(MessageKeys.MESSAGE);
                Message mensaje = new Message(origin, destination, message);
                this.cont.mostrarMensaje(mensaje);
                //controlador.requestMessage();
            }).on(MessagesTypes.SELECT_ROOM,(Object... args) -> {
                JSONArray mensajes = (JSONArray)args[0];
                ArrayList<Message> messageList = new ArrayList<>();
                for( Object mensaje : mensajes){
                    JSONObject jsonMensaje = (JSONObject) mensaje;
                    messageList.add(new Message(jsonMensaje.getString(MessageKeys.ORIGIN), jsonMensaje.getString(MessageKeys.DESTINATION), 
                            jsonMensaje.getString(MessageKeys.MESSAGE)));
                }
                this.cont.seleccionarSala(messageList);
                
            });
            usuario.setSocket(socket);
            this.usuario.getSocket().connect();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
    }

    private JSONObject getMessageJson(String destination, String message) {
        JSONObject userJson = new JSONObject();
        userJson.put(MessageKeys.ORIGIN, this.usuario.getNombre());
        userJson.put(MessageKeys.DESTINATION, destination);
        userJson.put(MessageKeys.MESSAGE, message);
        return userJson;
    }

    private JSONObject getUserJson() {
        JSONObject userJson = new JSONObject();
        userJson.put("userName", this.usuario.getNombre());
        return userJson;
    }
    
    private JSONObject getCambioSalaJson(String sala){
        JSONObject salaJson = new JSONObject();
        salaJson.put(MessageKeys.ORIGIN, this.usuario.getNombre());
        salaJson.put(MessageKeys.DESTINATION, sala);
        return salaJson;
    }
    
    public void cambiarSala(String sala){
        this.usuario.getSocket().emit(MessagesTypes.SELECT_ROOM,this.getCambioSalaJson(sala));
    }

    
    public void enviarMensaje(String mensaje, String destino) throws IOException {
        usuario.getSocket().emit(MessagesTypes.CHAT, getMessageJson(destino, mensaje));
    }
    
    public void desconectar(){
        this.usuario.getSocket().emit(MessagesTypes.DESCONECTAR,this.usuario.getNombre());
    }

    //Metodo que retorna todos los conectados
    public void conectados(String[] list) {
        String cad = "";
        for (int i = 1; i < list.length; i++) {
            cad += list[i] + "" + ",";
        }
        cad += "";
        this.cont.conectados(cad);

    }

    public boolean getContectados() {
        if (this.getConectados() > 2) {
            return true;
        } else {
            return false;
        }
    }

    public void responder(String nombre) throws IOException {

        System.out.print("\n" + "Responder:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String mensaje = br.readLine();
        this.enviarMensaje(mensaje, nombre);
      
    }

    /**
     * @return the conectados
     */
    public int getConectados() {
        return conectados;
    }

    /**
     * @param conectados the conectados to set
     */
    public void setConectados(int conectados) {
        this.conectados = conectados;
    }
}
