package com.infuse.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infuse.model.Pedido;
import com.infuse.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> criarPedidos(@RequestBody List<Pedido> pedidos) {
        try {
            List<Pedido> novosPedidos = pedidoService.criarPedidos(pedidos);
            return ResponseEntity.ok(novosPedidos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> consultarPedidos(
            @RequestParam(value = "numeroControle", required = false) String numeroControle,
            @RequestParam(value = "dataCadastro", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataCadastro) {
        List<Pedido> pedidos = pedidoService.buscarPedidos(numeroControle, dataCadastro);
        return ResponseEntity.ok(pedidos);
    }

}
