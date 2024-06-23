package com.estoque.gerenciador.controller;

import com.estoque.gerenciador.dtos.ProductRecordDto;
import com.estoque.gerenciador.models.ProductModel;
import com.estoque.gerenciador.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping
@RestController
@CrossOrigin (origins = "*", maxAge = 3600)
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }
    @GetMapping("/products")
    public ResponseEntity<CollectionModel<EntityModel<ProductModel>>> getAllProducts() {
        List<ProductModel> productsList = productRepository.findAll();

        List<EntityModel<ProductModel>> productsWithLinks = productsList.stream()
                .map(product -> {
                    UUID id = product.getIdProduct();
                    return EntityModel.of(product,
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getOneProduct(id)).withSelfRel());})
                .collect(Collectors.toList());

        CollectionModel<EntityModel<ProductModel>> collectionModel = CollectionModel.of(productsWithLinks,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getAllProducts()).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(collectionModel);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<EntityModel<ProductModel>> getOneProduct(@PathVariable UUID id) {
        Optional<ProductModel> productO = productRepository.findById(id);
        if (productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(EntityModel.of(productO.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getAllProducts()).withSelfRel()));
    }

    @PutMapping("/products/{id}")
        public ResponseEntity<Object> updateProduct(@PathVariable UUID id, @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> productO = productRepository.findById(id);
        if (productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto Nao Encontrado");
        }
        var productModel = productO.get();
        BeanUtils.copyProperties(productRecordDto, productModel, "idProduct");
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable UUID id) {
        Optional<ProductModel> productO = productRepository.findById(id);
        if (productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto nao Encontrado");
        }
        productRepository.delete(productO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto Deletado");
    }
}
