/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.com.br.bo;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.data.annotation.Id;

/**
 *
 * @author andre
 */
public class LoginMongo {

    @Id
    public String id;
    private String name = "";
    private String email;
    private String user_type = "normal";
    private String email_verified_at;
    private String password;
    private String created_at;
    private String updated_at;

    public LoginMongo() {
    }

    public LoginMongo(String id, String email, String email_verified_at, String password, String created_at, String updated_at) {
        this.id = id;
        this.email = email;
        this.email_verified_at = email_verified_at;
        this.password = password;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getEmail_verified_at() {
        return email_verified_at;
    }

    public void setEmail_verified_at(String email_verified_at) {
        this.email_verified_at = email_verified_at;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
    public void geraValoresDefault() {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date1 = simpleDateFormat.format(new Date());
        created_at = date1;
        updated_at = date1;
        name = email;

    }    

    /*
    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }
    */
}
