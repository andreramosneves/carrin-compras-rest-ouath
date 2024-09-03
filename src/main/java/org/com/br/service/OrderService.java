/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.com.br.service;

import java.util.List;
import org.com.br.repositories.OrderRepository;
import org.com.br.repositories.KartRepository;
import org.com.br.bo.Order;
import org.com.br.bo.Kart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author andre
 */
@Service
public class OrderService {

    private static final int PAGE_SIZE = 10;

    private final OrderRepository dao;

    private final KartRepository daoKart;

    private final NotificationService service;

    public OrderService(OrderRepository dao, KartRepository daoKart, NotificationService service) {
        this.dao = dao;
        this.daoKart = daoKart;
        this.service = service;
    }

    public Page<Order> listaPedidosPorUsuario(int id, int page) {
        return dao.listOrderByUser(id, PageRequest.of(page, OrderService.PAGE_SIZE));
    }

    public Order save(Order geraValoresDefault) {
        return dao.save(geraValoresDefault);
    }

    public List<Kart> listItensCarrinho(int id) {
        return daoKart.listItensCarrinho(id);
    }

    public void saveKart(Kart kart) {
        daoKart.save(kart);
    }

    public void sendMessage(Order order) {
        try {
           service.sendNotification("Ordem " + order.getId());
        } catch (Exception e) {

        }

    }

}
