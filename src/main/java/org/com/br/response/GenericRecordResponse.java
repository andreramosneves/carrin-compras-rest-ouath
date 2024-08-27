/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.com.br.response;

/**
 *
 * @author andre
 */
public class GenericRecordResponse {
 private String msgSucess;
    private boolean sucess;
    private Record v;


    
    public GenericRecordResponse(String msgSucess, boolean sucess, Record objeto) {
        this.msgSucess = msgSucess;
        this.sucess = sucess;
        this.v = objeto;
    }

    public String getMsgSucess() {
        return msgSucess;
    }

    public void setMsgSucess(String msgSucess) {
        this.msgSucess = msgSucess;
    }

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    public Record getObjeto() {
        return v;
    }

    public void setObjeto(Record objeto) {
        this.v = objeto;
    }
    
        
}
