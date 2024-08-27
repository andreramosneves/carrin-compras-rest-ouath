package org.com.br.controllers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.com.br.bo.Kart;
import org.com.br.bo.Login;
import org.com.br.bo.Order;
import org.com.br.dto.OrderDTO;
import org.com.br.response.GenericResponse;
import org.com.br.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping(path = "/list")
    @ResponseBody
    public ResponseEntity<Page> listOrders(@RequestParam(
            value = "page",
            required = false,
            defaultValue = "0") int page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Login credentials = (Login) authentication.getCredentials();

        List<OrderDTO> listOrder = service.listaPedidosPorUsuario(credentials.getId(), page)
                .stream()
                .map(b -> new OrderDTO(b.getId(),
                b.getTotal(),
                b.getCreated_at(),
                b.getDt_cadastro(),
                b.getDt_termino(),
                b.getDt_cancelamento()
        )).collect(Collectors.toList());

        Page<OrderDTO> pageDTO = new PageImpl(listOrder);

        return ResponseEntity.ok(pageDTO);
    }

    
   @PostMapping("/completeOrder")
   @ResponseBody
   public ResponseEntity<GenericResponse> completeOrder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Login credentials = (Login) authentication.getCredentials();

        List<Kart> carrinho = service.listItensCarrinho(credentials.getId());

        Order order = new Order();
        order.setUser(credentials);
        order.setTotal(carrinho.stream().map(item -> item.getValor_produto().multiply(item.getQuantidade())).mapToDouble(BigDecimal::doubleValue).sum());
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        if (violations.isEmpty()) {
            order = service.save(order.geraValoresDefault());
            for (Kart item : carrinho) {
                item.setOrder(order);
                service.saveKart(item);
            }
            return ResponseEntity.ok(new GenericResponse("Inserted with sucessfull",
                    true, new OrderDTO(order.getId(),
                            order.getTotal(),order.getCreated_at(),
                            order.getDt_cadastro(),
                            order.getDt_termino(),
                            order.getDt_cancelamento()
                    )));

        }
        return ResponseEntity.ok(new GenericResponse("Don't possible insert!!" + violations.toString(),
                false, null));
    }
  
}
