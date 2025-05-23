package com.l2code.embalagem.service;

import com.l2code.embalagem.model.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmbalagemService {

    private static final List<Caixa> CAIXAS_DISPONIVEIS = List.of(
            new Caixa(1, 30, 40, 80),
            new Caixa(2, 80, 50, 40),
            new Caixa(3, 50, 80, 60)
    );

    public ResultadoEmbalagem calcularEmbalagem(List<Pedido> pedidos) {
        ResultadoEmbalagem resultado = new ResultadoEmbalagem();
        List<ResultadoEmbalagem.ResultadoPedido> resultadosPedidos = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            ResultadoEmbalagem.ResultadoPedido resultadoPedido = new ResultadoEmbalagem.ResultadoPedido();
            resultadoPedido.setPedidoId(pedido.getId());
            
            List<ResultadoEmbalagem.CaixaComProdutos> caixasUsadas = new ArrayList<>();
            List<Produto> produtosRestantes = new ArrayList<>(pedido.getProdutos());
            
            // Ordena produtos por volume (do maior para o menor)
            produtosRestantes.sort((p1, p2) -> Double.compare(
                    p2.getAltura() * p2.getLargura() * p2.getComprimento(),
                    p1.getAltura() * p1.getLargura() * p1.getComprimento()
            ));
            
            while (!produtosRestantes.isEmpty()) {
                // Encontra a melhor caixa para os produtos restantes
                Map.Entry<Caixa, List<Produto>> melhorAlocacao = encontrarMelhorAlocacao(produtosRestantes);
                
                if (melhorAlocacao != null) {
                    Caixa caixaEscolhida = melhorAlocacao.getKey();
                    List<Produto> produtosAlocados = melhorAlocacao.getValue();
                    
                    ResultadoEmbalagem.CaixaComProdutos caixaComProdutos = new ResultadoEmbalagem.CaixaComProdutos();
                    caixaComProdutos.setCaixaId(caixaEscolhida.getId());
                    caixaComProdutos.setAltura(caixaEscolhida.getAltura());
                    caixaComProdutos.setLargura(caixaEscolhida.getLargura());
                    caixaComProdutos.setComprimento(caixaEscolhida.getComprimento());
                    caixaComProdutos.setProdutos(produtosAlocados);
                    
                    caixasUsadas.add(caixaComProdutos);
                    produtosRestantes.removeAll(produtosAlocados);
                } else {
                    // Não foi possível alocar mais produtos
                    break;
                }
            }
            
            resultadoPedido.setCaixas(caixasUsadas);
            resultadosPedidos.add(resultadoPedido);
        }
        
        resultado.setResultados(resultadosPedidos);
        return resultado;
    }
    
    private Map.Entry<Caixa, List<Produto>> encontrarMelhorAlocacao(List<Produto> produtos) {
        Map<Caixa, List<Produto>> alocacoesPossiveis = new HashMap<>();
        
        for (Caixa caixa : CAIXAS_DISPONIVEIS) {
            List<Produto> produtosAlocados = new ArrayList<>();
            double volumeOcupado = 0;
            
            for (Produto produto : produtos) {
                if (cabeProdutoNaCaixa(produto, caixa)) {
                    produtosAlocados.add(produto);
                    volumeOcupado += produto.getAltura() * produto.getLargura() * produto.getComprimento();
                }
            }
            
            if (!produtosAlocados.isEmpty()) {
                alocacoesPossiveis.put(caixa, produtosAlocados);
            }
        }
        
        if (alocacoesPossiveis.isEmpty()) {
            return null;
        }
        
        // Escolhe a alocação com maior eficiência (maior volume ocupado / volume da caixa)
        return alocacoesPossiveis.entrySet().stream()
                .max(Comparator.comparingDouble(entry -> {
                    Caixa caixa = entry.getKey();
                    List<Produto> produtosAlocados = entry.getValue();
                    double volumeOcupado = produtosAlocados.stream()
                            .mapToDouble(p -> p.getAltura() * p.getLargura() * p.getComprimento())
                            .sum();
                    return volumeOcupado / caixa.getVolume();
                }))
                .orElse(null);
    }
    
    private boolean cabeProdutoNaCaixa(Produto produto, Caixa caixa) {
        // Verifica todas as possíveis orientações do produto
        return (produto.getAltura() <= caixa.getAltura() && produto.getLargura() <= caixa.getLargura() && produto.getComprimento() <= caixa.getComprimento()) ||
               (produto.getAltura() <= caixa.getAltura() && produto.getLargura() <= caixa.getComprimento() && produto.getComprimento() <= caixa.getLargura()) ||
               (produto.getAltura() <= caixa.getLargura() && produto.getLargura() <= caixa.getAltura() && produto.getComprimento() <= caixa.getComprimento()) ||
               (produto.getAltura() <= caixa.getLargura() && produto.getLargura() <= caixa.getComprimento() && produto.getComprimento() <= caixa.getAltura()) ||
               (produto.getAltura() <= caixa.getComprimento() && produto.getLargura() <= caixa.getAltura() && produto.getComprimento() <= caixa.getLargura()) ||
               (produto.getAltura() <= caixa.getComprimento() && produto.getLargura() <= caixa.getLargura() && produto.getComprimento() <= caixa.getAltura());
    }
}