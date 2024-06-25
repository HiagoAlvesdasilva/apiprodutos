package com.estoque.gerenciador.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductRecordDto {

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal valor;

    @NotBlank
    private String descricao;

    @NotBlank
    private String marca;

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @NotNull BigDecimal getValor() {
        return valor;
    }

    public void setValor(@NotNull BigDecimal valor) {
        this.valor = valor;
    }

    public @NotBlank String getDescricao() {
        return descricao;
    }

    public void setDescricao(@NotBlank String descricao) {
        this.descricao = descricao;
    }

    public @NotBlank String getMarca() {
        return marca;
    }

    public void setMarca(@NotBlank String marca) {
        this.marca = marca;
    }
}
