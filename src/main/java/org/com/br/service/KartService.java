/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.com.br.service;

import java.util.List;
import java.util.Optional;
import org.com.br.repositories.KartRepository;
import org.com.br.repositories.ProductRepository;
import org.com.br.bo.Kart;
import org.com.br.bo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author andre
 */
@Service
public class KartService {

    @Autowired
    private KartRepository daoKart;
    @Autowired
    private ProductRepository daoProduct;

    public List<Kart> listItensCarrinho(int id) {
        return daoKart.listItensCarrinho(id);
    }

    public Product findProduct(Long parseInt) {
        return daoProduct.findById(parseInt).get();
    }

    public Kart save(Kart kart) {
        return daoKart.save(kart);
    }

    public Optional<Kart> findKartByUser(int parseInt, int UserId) {
        return daoKart.findKartAndUser(parseInt,UserId);
    }

}
