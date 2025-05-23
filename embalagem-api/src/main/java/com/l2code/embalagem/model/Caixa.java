package com.l2code.embalagem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Caixa {
    private int id;
    private double altura;
    private double largura;
    private double comprimento;
    private double volume;
    
    public Caixa(int id, double altura, double largura, double comprimento) {
        this.id = id;
        this.altura = altura;
        this.largura = largura;
        this.comprimento = comprimento;
        this.volume = altura * largura * comprimento;
    }
}