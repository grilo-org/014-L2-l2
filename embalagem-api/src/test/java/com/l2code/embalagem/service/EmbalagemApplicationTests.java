package com.l2code.embalagem.service;

import com.l2code.embalagem.model.Pedido;
import com.l2code.embalagem.model.Produto;
import com.l2code.embalagem.model.ResultadoEmbalagem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmbalagemServiceTest {

    @Autowired
    private EmbalagemService embalagemService;

    @Test
    void testCalcularEmbalagem() {
        // Criar produtos
        Produto produto1 = new Produto();
        produto1.setId(UUID.randomUUID().toString());
        produto1.setAltura(20);
        produto1.setLargura(30);
        produto1.setComprimento(70);

        Produto produto2 = new Produto();
        produto2.setId(UUID.randomUUID().toString());
        produto2.setAltura(10);
        produto2.setLargura(20);
        produto2.setComprimento(30);

        // Criar lista de produtos
        List<Produto> produtos = new ArrayList<>();
        produtos.add(produto1);
        produtos.add(produto2);

        // Criar pedido
        Pedido pedido = new Pedido();
        pedido.setId(UUID.randomUUID().toString());
        pedido.setProdutos(produtos);

        // Criar lista de pedidos
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(pedido);

        // Executar o serviço de embalagem
        ResultadoEmbalagem resultado = embalagemService.calcularEmbalagem(pedidos);

        // Verificações
        assertNotNull(resultado);
        assertNotNull(resultado.getResultados());
        assertEquals(1, resultado.getResultados().size());
        
        ResultadoEmbalagem.ResultadoPedido resultadoPedido = resultado.getResultados().get(0);
        assertEquals(pedido.getId(), resultadoPedido.getPedidoId());
        assertNotNull(resultadoPedido.getCaixas());
        assertFalse(resultadoPedido.getCaixas().isEmpty());
        
        // Verifica se todos os produtos foram alocados
        int totalProdutosAlocados = resultadoPedido.getCaixas().stream()
                .mapToInt(caixa -> caixa.getProdutos().size())
                .sum();
        assertEquals(produtos.size(), totalProdutosAlocados);
    }
}