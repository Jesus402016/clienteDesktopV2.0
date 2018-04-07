/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.gui;

/**
 *
 * @author Jesus
 */

public class  estilo {
   
    private static String HtmlR="font-family: Century Gothic,CenturyGothic,AppleGothic,sans-serif;color: #ffffff;" +
            "font-size: 10px;font-weight: 20;text-align: right; background: #36b728;margin: 0 0 5px;" +
            "overflow: hidden;padding: 20px;border-radius: 35px 0px 35px 0px;border: 2px solid #5878ca;";
   private static String HtmlL="font-family: Century Gothic,CenturyGothic,AppleGothic,sans-serif;color: #ffffff;" +
            "font-size: 10px;font-weight: 20;text-align: left; background: #888ccf;margin: 0 0 5px;" +
            "overflow: hidden;padding: 20px;border-radius: 35px 0px 35px 0px;border: 2px solid #5878ca;";


public static String htmlEnviado(String men){
    String cad="";
   cad+="<div style=\""+HtmlR+"\">"+men+"</div> ";
   return cad;
}

public static  String htmlRecibido(String men){
    String cad="";
   cad+="<div style=\""+HtmlL+"\" >"+men+"</div> ";
   return cad;
}

}



