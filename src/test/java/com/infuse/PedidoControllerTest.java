package com.infuse;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.infuse.controller.PedidoController;
import com.infuse.model.Pedido;
import com.infuse.service.PedidoService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoController pedidoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pedidoController).build();
    }

    @Test
    public void deveRetornarPedidosPorNumeroControle() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setNumeroControle("12345");
        pedido.setNomeProduto("Produto A");
        
        when(pedidoService.buscarPedidos("12345", null))
                .thenReturn(Arrays.asList(pedido));

        mockMvc.perform(get("/api/pedidos")
                .param("numeroControle", "12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroControle", is("12345")));
    }

    @Test
    public void deveRetornarPedidosPorDataCadastro() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setNumeroControle("12345");
        pedido.setDataCadastro(LocalDate.of(2024, 8, 26));
        
        when(pedidoService.buscarPedidos(null, LocalDate.of(2024, 8, 26)))
                .thenReturn(Arrays.asList(pedido));

        mockMvc.perform(get("/api/pedidos")
                .param("dataCadastro", "2024-08-26"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dataCadastro", is("2024-08-26")));
    }


    @Test
    public void deveRetornarTodosOsPedidos() throws Exception {
        Pedido pedido1 = new Pedido();
        pedido1.setNumeroControle("12345");
        Pedido pedido2 = new Pedido();
        pedido2.setNumeroControle("12346");
        
        when(pedidoService.buscarPedidos(null, null))
                .thenReturn(Arrays.asList(pedido1, pedido2));

        mockMvc.perform(get("/api/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroControle", is("12345")))
                .andExpect(jsonPath("$[1].numeroControle", is("12346")));
    }
}
