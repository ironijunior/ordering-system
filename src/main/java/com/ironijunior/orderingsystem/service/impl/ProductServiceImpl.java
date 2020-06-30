package com.ironijunior.orderingsystem.service.impl;

import com.ironijunior.orderingsystem.domain.Product;
import com.ironijunior.orderingsystem.exception.ProductNotFoundException;
import com.ironijunior.orderingsystem.repository.ProductRepository;
import com.ironijunior.orderingsystem.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        if (Objects.isNull(product)) {
            throw new IllegalArgumentException();
        }

        product.setCreatedAt(LocalDateTime.now());
        return productRepository.saveAndFlush(product);
    }

    @Override
    public Product update(Product product) {
        if (Objects.isNull(product)) {
            throw new IllegalArgumentException();
        }

        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.saveAndFlush(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product get(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product udpateStock(Long id, Long quantity) {
        Product product = get(id);
        product.setQuantity(quantity);
        return update(product);
    }
}
