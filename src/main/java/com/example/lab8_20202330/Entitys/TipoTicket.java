package com.example.lab8_20202330.Entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tipo_ticket_evento")
public class TipoTicket{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "precio", nullable = false)
    private String precio;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
    @ManyToOne(optional = false)
    @JoinColumn(name = "idEvento")
    private Evento idEvento;


}