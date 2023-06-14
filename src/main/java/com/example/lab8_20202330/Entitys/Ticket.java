package com.example.lab8_20202330.Entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ticket")
public class Ticket{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idTipoTicket")
    private TipoTicket idTipoTicket;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idUsuario")
    private Usuario idUsuario;

}
