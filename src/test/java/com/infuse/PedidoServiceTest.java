package com.infuse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.infuse.model.Pedido;
import com.infuse.repository.PedidoRepository;
import com.infuse.service.PedidoService;

@SpringBootTest
public class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveBuscarPedidosPorNumeroControle() {
        Pedido pedido = new Pedido();
        pedido.setNumeroControle("12345");
        pedido.setNomeProduto("Produto A");
        pedido.setValorUnitario(50.0);
        pedido.setQuantidade(1);
        pedido.setCodigoCliente(1L);
        pedido.setDataCadastro(LocalDate.now());
        
        when(pedidoRepository.findByFilters("12345", null))
                .thenReturn(Arrays.asList(pedido));

        List<Pedido> pedidos = pedidoService.buscarPedidos("12345", null);

        assertEquals(1, pedidos.size());
        assertEquals("12345", pedidos.get(0).getNumeroControle());
    }

    @Test
    public void deveBuscarPedidosPorDataCadastro() {
        Pedido pedido = new Pedido();
        pedido.setNumeroControle("12345");
        pedido.setNomeProduto("Produto A");
        pedido.setValorUnitario(50.0);
        pedido.setQuantidade(1);
        pedido.setCodigoCliente(1L);
        pedido.setDataCadastro(LocalDate.of(2024, 8, 26));
        
        when(pedidoRepository.findByFilters(null, LocalDate.of(2024, 8, 26)))
                .thenReturn(Arrays.asList(pedido));

        List<Pedido> pedidos = pedidoService.buscarPedidos(null, LocalDate.of(2024, 8, 26));

        assertEquals(1, pedidos.size());
        assertEquals(LocalDate.of(2024, 8, 26), pedidos.get(0).getDataCadastro());
    }

    @Test
    public void deveBuscarTodosOsPedidos() {
        Pedido pedido1 = new Pedido();
        pedido1.setNumeroControle("12345");
        Pedido pedido2 = new Pedido();
        pedido2.setNumeroControle("12346");
        
        when(pedidoRepository.findByFilters(null, null))
                .thenReturn(Arrays.asList(pedido1, pedido2));

        List<Pedido> pedidos = pedidoService.buscarPedidos(null, null);

        assertEquals(2, pedidos.size());
    }
}
