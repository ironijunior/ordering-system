package com.ironijunior.orderingsystem.controller;

import com.ironijunior.orderingsystem.adapter.ProductAdapter;
import com.ironijunior.orderingsystem.controller.dto.product.ProductRequest;
import com.ironijunior.orderingsystem.controller.dto.product.ProductResponse;
import com.ironijunior.orderingsystem.domain.Product;
import com.ironijunior.orderingsystem.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Exposes an API to manage products.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest request) {
        Product product = service.create(ProductAdapter.toDomain(request));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProductAdapter.toResponse(product));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductResponse> get(@PathVariable Long id) {
        return ResponseEntity
                .ok(ProductAdapter.toResponse(service.get(id)));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ProductResponse> updateStock(@PathVariable Long id, @RequestBody Long quantity) {
        return ResponseEntity
                .ok(ProductAdapter.toResponse(service.udpateStock(id, quantity)));
    }

}
