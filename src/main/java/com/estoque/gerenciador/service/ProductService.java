package com.estoque.gerenciador.service;

import com.estoque.gerenciador.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements ProductImpl {

    final
    ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

}
