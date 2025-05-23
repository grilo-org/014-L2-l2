package com.l2code.embalagem.model;

import lombok.Data;

@Data
public class Produto {
    private String id;
    private double altura;
    private double largura;
    private double comprimento;
}