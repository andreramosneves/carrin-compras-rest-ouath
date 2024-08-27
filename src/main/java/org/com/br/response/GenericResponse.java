/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package org.com.br.response;

/**
 *
 * @author andre
 * @param <T>
 */
//<T> extends T
public class GenericResponse<T> {
    
    private String msgSucess;
    private boolean sucess;
    private T v;

    public GenericResponse() {
    }

    
    public GenericResponse(String msgSucess, boolean sucess, T objeto) {
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

    public T getObjeto() {
        return v;
    }

    public void setObjeto(T objeto) {
        this.v = objeto;
    }
    
    
    
}
