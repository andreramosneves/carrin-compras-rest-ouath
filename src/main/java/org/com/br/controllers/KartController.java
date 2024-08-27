package org.com.br.controllers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.com.br.bo.Kart;
import org.com.br.bo.Login;
import org.com.br.bo.Product;
import org.com.br.dto.KartDTO;
import org.com.br.request.KartRequest;
import org.com.br.response.GenericRecordResponse;
import org.com.br.service.KartService;
import org.com.br.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kart")  // Prefixo comum para todos os endpoints deste controlador
public class KartController {

    @Autowired
    private KartService service;

    @Autowired
    private ProductService serviceProduct;

    /*Por ser só um projeto de apresentação, a modelagem de dados está bem inconsistente,
    teria que ter uma tabela de Item de Carrinho One To Many.
     */
    @GetMapping(path = "/list", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List> showCarrinho() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Login credentials = (Login) authentication.getCredentials();

        List<KartDTO> listKart = service.listItensCarrinho(credentials.getId()).stream().map(b -> new KartDTO(
                b.getId(), b.getProduct(),
                b.getQuantidade(),
                b.getValor_produto(),
                b.getDt_termino()
        )).collect(Collectors.toList());
        return ResponseEntity.ok(listKart);
    }

    @PostMapping(path = "/add/{id}")
    @ResponseBody
    public ResponseEntity<GenericRecordResponse> addKart(
            @RequestBody KartRequest data,
            @PathVariable Long id
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Login credentials = (Login) authentication.getCredentials();

        // Processa os dados recebidos
        Product product = serviceProduct.find(id);
        Kart kart = new Kart();
        kart.setUser(credentials);
        kart.setProduct(product);
        kart.setQuantidade(data.quantidade());
        kart.setValor_produto(product.getValor_produto().multiply(data.quantidade()));

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Kart>> violations = validator.validate(kart);
        if (violations.isEmpty()) {
            kart = service.save(kart.geraValoresDefault());
            KartDTO dto = new KartDTO(kart.getId(), kart.getProduct(),
                    kart.getQuantidade(),
                    kart.getValor_produto(),
                    kart.getDt_termino()
            );

            GenericRecordResponse g = new GenericRecordResponse("Item adicionado ao carrinho com sucesso!", true, dto);
            return ResponseEntity.ok(g);

        } else {
            GenericRecordResponse g = new GenericRecordResponse(violations.toString(), false, null);
            return ResponseEntity.ok(g);

        }
    }

    /* Cancelar todos os itens de um carrinho */
    @GetMapping(path = "/cancelAll")
    @ResponseBody
    public ResponseEntity<List> cancel() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Login credentials = (Login) authentication.getCredentials();

        List<Kart> listKart = service.listItensCarrinho(credentials.getId());
        List<KartDTO> dto = new ArrayList<>();
        for (Kart kart : listKart) {
            kart = kart.finalizaKart();
            Kart k = service.save(kart);
            dto.add(new KartDTO(k.getId(), k.getProduct(), k.getQuantidade(), k.getValor_produto(), k.getDt_termino()));
        }
        return ResponseEntity.ok(dto);

    }

    /* Cancelar todos os itens de um carrinho */
    @GetMapping(path = "/cancel/{idKart}")
    @ResponseBody
    public ResponseEntity<GenericRecordResponse> cancelItem(@PathVariable Integer idKart) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Login credentials = (Login) authentication.getCredentials();

        Optional<Kart> k = service.findKartByUser(idKart, credentials.getId());
        if (!k.isEmpty()) {
            Kart k1 = k.get();
            k1.finalizaKart();
            k1 = service.save(k1);
            KartDTO dto = new KartDTO(k1.getId(),
                    k1.getProduct(),
                    k1.getQuantidade(),
                    k1.getValor_produto(), k1.getDt_termino());

            return ResponseEntity.ok(new GenericRecordResponse("Item cancelado com sucesso!", true, dto));
        } else {
            return ResponseEntity.ok(new GenericRecordResponse("Não foi possível cancelar o item do Carrinho!", false, null));
        }

    }

}
