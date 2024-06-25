package com.estoque.gerenciador.controller;

import com.estoque.gerenciador.dtos.ProductRecordDto;
import com.estoque.gerenciador.dtos.ProductResponseDto;
import com.estoque.gerenciador.exceptions.ProductNotFoundException;
import com.estoque.gerenciador.models.ProductModel;
import com.estoque.gerenciador.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Adicionar Um Produto", description = "Metodo Que Cria Um Novo Produto")
    @PostMapping
    public ResponseEntity<ProductResponseDto> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        ProductModel savedProduct = productService.saveProduct(productRecordDto);
        ProductResponseDto responseDto = new ProductResponseDto(savedProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(summary = "Exibir Todos Os Produtos", description = "Metodo Que Exibe Uma Lista Com Todos Os Produtos Cadastrados")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ProductResponseDto>>> getAllProducts() {
        List<ProductModel> productsList = productService.getAllProducts();

        List<EntityModel<ProductResponseDto>> productsWithLinks = productsList.stream()
                .map(product -> {
                    ProductResponseDto responseDto = new ProductResponseDto(product);
                    UUID id = product.getIdProduct();
                    return EntityModel.of(responseDto,
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
                })
                .collect(Collectors.toList());

        CollectionModel<EntityModel<ProductResponseDto>> collectionModel = CollectionModel.of(productsWithLinks,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getAllProducts()).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(collectionModel);
    }

    @Operation(summary = "Exibir Apenas Um Produto", description = "Metodo Que Exibe Um Produto Especifico Atraves Do ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProductResponseDto>> getOneProduct(@PathVariable UUID id) {
        ProductModel product = productService.getProductById(id);
        ProductResponseDto responseDto = new ProductResponseDto(product);
        return ResponseEntity.status(HttpStatus.OK).body(EntityModel.of(responseDto,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getAllProducts()).withSelfRel()));
    }

    @Operation(summary = "Atualizar Um Produto", description = "Metodo  Que Atualiza Um Produto Especifico Atraves Do ID")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable UUID id, @RequestBody @Valid ProductRecordDto productRecordDto) {
        ProductModel updatedProduct = productService.updateProduct(id, productRecordDto);
        ProductResponseDto responseDto = new ProductResponseDto(updatedProduct);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "Apagar Um Produto", description = "Metodo Que Apaga Um Produto Especifico Atraves do ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body("Produto Deletado");
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
