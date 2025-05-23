package com.l2code.embalagem.controller;

import com.l2code.embalagem.model.Pedido;
import com.l2code.embalagem.model.ResultadoEmbalagem;
import com.l2code.embalagem.service.EmbalagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/embalagem")
@Tag(name = "Embalagem", description = "API para cálculo de embalagem de pedidos")
public class EmbalagemController {

    private final EmbalagemService embalagemService;

    @Autowired
    public EmbalagemController(EmbalagemService embalagemService) {
        this.embalagemService = embalagemService;
    }

    @PostMapping("/calcular")
    @Operation(
        summary = "Calcula a melhor forma de embalar os pedidos",
        description = "Recebe uma lista de pedidos com produtos e suas dimensões, e retorna a melhor forma de embalá-los nas caixas disponíveis"
    )
    public ResponseEntity<ResultadoEmbalagem> calcularEmbalagem(@RequestBody List<Pedido> pedidos) {
        ResultadoEmbalagem resultado = embalagemService.calcularEmbalagem(pedidos);
        return ResponseEntity.ok(resultado);
    }
}