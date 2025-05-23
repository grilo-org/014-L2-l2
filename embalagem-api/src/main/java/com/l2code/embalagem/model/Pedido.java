package com.l2code.embalagem.model;

import lombok.Data;
import java.util.List;

@Data
public class Pedido {
    private String id;
    private List<Produto> produtos;
}