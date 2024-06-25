package com.estoque.gerenciador.dtos;

import com.estoque.gerenciador.models.ProductModel;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductResponseDto {

    private UUID idProduct;
    private String name;
    private BigDecimal valor;
    private String descricao;
    private String marca;

    public ProductResponseDto(ProductModel productModel) {
        this.idProduct = productModel.getIdProduct();
        this.name = productModel.getName();
        this.valor = productModel.getValor();
        this.descricao = productModel.getDescricao();
        this.marca = productModel.getMarca();
    }

    public UUID getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(UUID idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}
