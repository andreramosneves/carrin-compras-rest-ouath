/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.com.br.service;

import org.com.br.repositories.ProductRepository;
import org.com.br.bo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author andre
 */
@Service
public class ProductService {

    private static final int PAGE_SIZE = 10;

    @Autowired
    private ProductRepository dao;

    public Product save(Product produto) {
        return dao.save(produto);
    }

    public Page<Product> list(int page) {
        return dao.findByDtterminoNull(PageRequest.of(page, ProductService.PAGE_SIZE));
    }

    public Product find(Long parseInt) {
        return dao.findById(parseInt).get();
    }

}
