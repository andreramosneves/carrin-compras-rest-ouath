package org.com.br.controllers;

import org.com.br.interfaces.StorageService;
import org.com.br.request.ProductRequest;
import org.com.br.response.GenericResponse;
import org.com.br.service.ProductService;
import org.com.br.bo.Product;
import org.com.br.config.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/product")  // Prefixo comum para todos os endpoints deste controlador
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private StorageService storage;

    private final StorageProperties storageProperties;

    public ProductController(StorageProperties properties) {
        this.storageProperties = properties;
    }

    @RequestMapping("/list")
    @ResponseBody
    public Page<Product> productView(@RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0") int page) {
        return service.list(page);
    }

    /*
    Não havia necessidade de criar o Builder, porém foi criado só para fins de estudo, o ideal era no Produto ter N Classes, 
    mas a construção foi feita com variáveis mesmo. Daria para substituir por um Lombok ou etc.
     */
    @ResponseBody
    @PostMapping(value = "/insert", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<GenericResponse> inseriProduto(@RequestBody ProductRequest produto) {
        Product.Builder builder = new Product.Builder();
        Product product = builder
                .withName(produto.nm_produto())
                .withValue(produto.valor_produto())
                .build();
        product.geraValoresDefault();
        return ResponseEntity.ok(new GenericResponse("Inserted with sucessfull",
                true, service.save(product)));
    }

    @PutMapping("/inactive/{id}")
    @ResponseBody
    public ResponseEntity<GenericResponse> inactive(@PathVariable Long id) {
        GenericResponse<Product> g;
//("Updated with sucessfull", true);
        Product p = service.find(id);
        if (p.getDt_termino().isEmpty()) {
            p = service.save(p.finalizaProduto());
            g = new GenericResponse("Updated with sucessfull", true, p);
        } else {
            g = new GenericResponse<>("Don't can updated with sucessfull", true, p);

        }
        return ResponseEntity.ok(g);

    }

    @PostMapping("/upload/{id}")
    @ResponseBody
    public ResponseEntity<GenericResponse> handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable Long id
    //		,RedirectAttributes redirectAttributes
    ) {
        GenericResponse<Product> g;

        Product p = service.find(id);
        if (p.getDt_termino().isEmpty()) {
            storage.init();
            storage.store(file);
            p.setPhoto(storageProperties.getLocation() + "/" + file.getOriginalFilename());
            p = service.save(p);
            g = new GenericResponse("Updated with sucessfull", true, p);
        } else {
            g = new GenericResponse<>("Don't can updated with sucessfull, inactive product!!", true, p);

        }

        return ResponseEntity.ok(g);
    }

}
