package org.example;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@ToString
@Setter
@Getter
@Builder

@Entity
public class Factura implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "fecha")
    private String fecha;

    @Column(name = "numero")
    private int numero;

    //Porque solo el cascadeo de Persist, porque persiste si el cliente no existe

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_cliente")

    private Cliente cliente;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    //Se agrega el join column para q JPA no cree la tabla intermedia en una relacón
    //de esta manera pone el foreing key 'factura_id' en la tabla de los many
    @JoinColumn(name = "factura_id")
    //Se agrega el builder.default para q el builder no sobreescriba la inicilización

    @Builder.Default
    private Set<DetalleFactura> facturas = new HashSet<>();

    public Factura(String fecha, int numero) {
        this.fecha = fecha;
        this.numero = numero;
    }

    public Factura() {
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<DetalleFactura> getFacturas() {
        return facturas;
    }

    public void setFacturas(Set<DetalleFactura> facturas) {
        this.facturas = facturas;
    }

}
