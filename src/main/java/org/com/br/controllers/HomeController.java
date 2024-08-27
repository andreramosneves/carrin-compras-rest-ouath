/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.com.br.controllers;

import org.com.br.bo.Product;
import org.com.br.config.StorageProperties;
import org.com.br.interfaces.StorageService;
import org.com.br.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author andre
 */

@RestController
@RequestMapping("/api/home")
public class HomeController {


    @Autowired
    private ProductService service;

    @RequestMapping("/list")
    @ResponseBody
    public Page<Product> productView(@RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0") int page) {
        return service.list(page);
    }
    
}
