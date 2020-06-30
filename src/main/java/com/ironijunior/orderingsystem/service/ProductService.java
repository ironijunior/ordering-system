package com.ironijunior.orderingsystem.service;

import com.ironijunior.orderingsystem.domain.Product;

public interface ProductService {

    Product create(Product product);

    Product update(Product product);

    void delete(Long id);

    Product get(Long id);

    Product udpateStock(Long id, Long quantity);

}
