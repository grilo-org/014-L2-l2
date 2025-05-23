package com.l2code.embalagem.model;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class ResultadoEmbalagem {
    private List<ResultadoPedido> resultados;
    
    @Data
    public static class ResultadoPedido {
        private String pedidoId;
        private List<CaixaComProdutos> caixas;
    }
    
    @Data
    public static class CaixaComProdutos {
        private int caixaId;
        private double altura;
        private double largura;
        private double comprimento;
        private List<Produto> produtos;
    }
}