package com.infuse.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "product_order")
public class Pedido {

	@EqualsAndHashCode.Include
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String numeroControle;
    
    @Column(nullable = false)
    private String nomeProduto;

    @Column(nullable = false)
    private Double valorUnitario;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private Double valorTotal;

    @Column(nullable = false)
    private Long codigoCliente;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(nullable = false)   
    private LocalDate dataCadastro;
	
	
	
}
