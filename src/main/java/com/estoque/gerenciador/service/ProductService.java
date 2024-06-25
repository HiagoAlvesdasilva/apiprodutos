package com.estoque.gerenciador.service;

import com.estoque.gerenciador.dtos.ProductRecordDto;
import com.estoque.gerenciador.exceptions.ProductNotFoundException;
import com.estoque.gerenciador.models.ProductModel;
import com.estoque.gerenciador.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductModel saveProduct(ProductRecordDto productRecordDto) {
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return productRepository.save(productModel);
    }

    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductModel getProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public ProductModel updateProduct(UUID id, ProductRecordDto productRecordDto) {
        ProductModel productModel = getProductById(id);
        BeanUtils.copyProperties(productRecordDto, productModel, "idProduct");
        return productRepository.save(productModel);
    }

    public void deleteProduct(UUID id) {
        ProductModel productModel = getProductById(id);
        productRepository.delete(productModel);
    }
}
