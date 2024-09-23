/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.com.br.repositories;

import org.com.br.bo.LoginMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author andre
 */

public interface LoginMongoRepository extends MongoRepository<LoginMongo, String> {

}

