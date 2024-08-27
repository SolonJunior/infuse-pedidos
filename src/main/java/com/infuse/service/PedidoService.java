package com.infuse.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infuse.model.Pedido;
import com.infuse.repository.PedidoRepository;

@Service
public class PedidoService {
	
	@Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> criarPedidos(List<Pedido> pedidos) throws Exception {
        validarQuantidadePedidos(pedidos);

        List<Pedido> pedidosSalvos = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            validarPedido(pedido);
            pedidosSalvos.add(pedidoRepository.save(pedido));
        }
        return pedidosSalvos;
    }

    private void validarQuantidadePedidos(List<Pedido> pedidos) throws Exception {
        if (pedidos.size() < 1 || pedidos.size() > 10) {
            throw new Exception("A quantidade de pedidos deve ser entre 1 e 10.");
        }
    }

    private void validarPedido(Pedido pedido) throws Exception {
        if (pedidoRepository.findByNumeroControle(pedido.getNumeroControle()).isPresent()) {
            throw new Exception("Número de controle já cadastrado: " + pedido.getNumeroControle());
        }

        if (pedido.getDataCadastro() == null) {
            pedido.setDataCadastro(LocalDate.now());
        }

        if (pedido.getQuantidade() == null) {
            pedido.setQuantidade(1);
        }

        if (pedido.getCodigoCliente() == null || pedido.getCodigoCliente() < 1 || pedido.getCodigoCliente() > 10) {
            throw new Exception("Código de cliente inválido: " + pedido.getCodigoCliente());
        }

        double desconto = 0;
        if (pedido.getQuantidade() >= 10) {
            desconto = 0.10;
        } else if (pedido.getQuantidade() > 5) {
            desconto = 0.05;
        }

        double valorTotal = pedido.getValorUnitario() * pedido.getQuantidade() * (1 - desconto);
        pedido.setValorTotal(valorTotal);
    }
    
    public List<Pedido> buscarPedidos(String numeroControle, LocalDate dataCadastro) {
        return pedidoRepository.findByFilters(numeroControle, dataCadastro);
    }
}

