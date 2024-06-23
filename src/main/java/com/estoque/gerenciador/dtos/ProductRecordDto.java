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


}
